package ru.klekchyan.quizEngine.question_ktor

import io.ktor.client.plugins.websocket.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.questions_api.questionApiV1Mapper
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V1QuestionWsStubApiTest {
    @Test
    fun createStub() {
        val request = QuestionCreateRequest(
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

        testMethod<IResponseQuestion>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun readStub() {
        val request = QuestionReadRequest(
            requestId = "12345",
            question = QuestionReadObject("666"),
            debug = QuestionDebug(
                mode = QuestionRequestDebugMode.STUB,
                stub = QuestionRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponseQuestion>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun updateStub() {
        val request = QuestionUpdateRequest(
            requestId = "12345",
            question = QuestionUpdateObject(
                id = "666"
            ),
            debug = QuestionDebug(
                mode = QuestionRequestDebugMode.STUB,
                stub = QuestionRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponseQuestion>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun deleteStub() {
        val request = QuestionDeleteRequest(
            requestId = "12345",
            question = QuestionDeleteObject(
                id = "666",
            ),
            debug = QuestionDebug(
                mode = QuestionRequestDebugMode.STUB,
                stub = QuestionRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponseQuestion>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun readAllStub() {
        val request = QuestionReadAllRequest(
            requestId = "12345",
            selector = QuestionsSelector(gameId = "1", roundId = "1"),
            debug = QuestionDebug(
                mode = QuestionRequestDebugMode.STUB,
                stub = QuestionRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponseQuestion>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    private inline fun <reified T> testMethod(
        request: IRequestQuestion,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {

        application { module() }

        environment {
            config = MapApplicationConfig()
        }

        val client = createClient {
            install(io.ktor.client.plugins.websocket.WebSockets)
        }

        client.webSocket("questions/v1/ws") {
            withTimeout(3000) {
                val incame = incoming.receive() as Frame.Text
                val response = questionApiV1Mapper.decodeFromString<T>(incame.readText())
                assertIs<QuestionInitResponse>(response)
            }
            send(Frame.Text(questionApiV1Mapper.encodeToString(request)))
            withTimeout(3000) {
                val incame = incoming.receive() as Frame.Text
                val text = incame.readText()
                val response = questionApiV1Mapper.decodeFromString<T>(text)

                assertBlock(response)
            }
        }
    }
}