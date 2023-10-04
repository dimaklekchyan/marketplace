package ru.klekchyan.quizEngine.stubs

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.questionApiV1Mapper
import kotlin.test.assertEquals

class V1QuestionStubApiTest {

    @Test
    fun create() = testApplication {
        val response = client.post("questions/v1/create") {
            val requestObj = QuestionCreateRequest(
                requestId = "12345",
                question = QuestionCreateObject(
                    roundId = "555",
                    gameId = "999",
                    questionType = QuestionType.OPEN_QUESTION,
                    formulation = "What's the count of venus from the sun?"
                ),
                debug = QuestionDebug(
                    mode = QuestionRequestDebugMode.STUB,
                    stub = QuestionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = questionApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = questionApiV1Mapper.decodeFromString<QuestionCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.question?.id)
    }

    @Test
    fun read() = testApplication {
        val response = client.post("questions/v1/read") {
            val requestObj = QuestionReadRequest(
                requestId = "12345",
                question = QuestionReadObject(
                    id = "1"
                ),
                debug = QuestionDebug(
                    mode = QuestionRequestDebugMode.STUB,
                    stub = QuestionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = questionApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = questionApiV1Mapper.decodeFromString<QuestionReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.question?.id)
    }

    @Test
    fun update() = testApplication {
        val response = client.post("questions/v1/update") {
            val requestObj = QuestionUpdateRequest(
                requestId = "12345",
                question = QuestionUpdateObject(
                    //...
                ),
                debug = QuestionDebug(
                    mode = QuestionRequestDebugMode.STUB,
                    stub = QuestionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = questionApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = questionApiV1Mapper.decodeFromString<QuestionUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.question?.id)
    }

    @Test
    fun delete() = testApplication {
        val response = client.post("questions/v1/delete") {
            val requestObj = QuestionDeleteRequest(
                requestId = "12345",
                question = QuestionDeleteObject(
                    //...
                ),
                debug = QuestionDebug(
                    mode = QuestionRequestDebugMode.STUB,
                    stub = QuestionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = questionApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = questionApiV1Mapper.decodeFromString<QuestionDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.question?.id)
    }

    @Test
    fun readAll() = testApplication {
        val response = client.post("questions/v1/readAll") {
            val requestObj = QuestionReadAllRequest(
                requestId = "12345",
                selector = QuestionsSelector(
                    gameId = "1",
                    roundId = "2"
                ),
                debug = QuestionDebug(
                    mode = QuestionRequestDebugMode.STUB,
                    stub = QuestionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = questionApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = questionApiV1Mapper.decodeFromString<QuestionReadAllResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(3, responseObj.questions?.size)
        assertEquals(QuestionType.OPEN_QUESTION, responseObj.questions?.get(0)?.questionType)
    }
}
