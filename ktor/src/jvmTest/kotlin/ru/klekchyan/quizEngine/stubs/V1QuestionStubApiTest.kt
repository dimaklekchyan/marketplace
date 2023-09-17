package ru.klekchyan.quizEngine.stubs

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.apiV1Mapper
import kotlin.test.assertEquals

class V1QuestionStubApiTest {

    @Test
    fun create() = testApplication {
        val response = client.post("/v1/question/create") {
            val requestObj = QuestionCreateRequest(
                requestId = "12345",
                question = QuestionCreateObject(
                    roundId = 555,
                    gameId = 999,
                    questionType = QuestionType.OPEN_QUESTION,
                    formulation = "What's the count of venus from the sun?"
                ),
                debug = QuestionDebug(
                    mode = RequestDebugMode.STUB,
                    stub = QuestionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<QuestionCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(123, responseObj.question?.id)
    }
}
