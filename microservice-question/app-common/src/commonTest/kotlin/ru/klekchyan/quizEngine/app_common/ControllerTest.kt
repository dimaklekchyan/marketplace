package ru.klekchyan.quizEngine.app_common

import kotlinx.coroutines.test.runTest
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_common.QuizQuestionCorSettings
import ru.klekchyan.quizEngine.question_mappers.fromTransport
import ru.klekchyan.quizEngine.question_mappers.toTransport
import kotlin.test.Test
import kotlin.test.assertEquals

class ControllerTest {

    private val request = QuestionCreateRequest(
        requestType = "create",
        requestId = "1234",
        question = QuestionCreateObject(
            gameId = "1",
            roundId = "1",
            questionType = QuestionType.OPEN_QUESTION,
            formulation = "Formulation"
        ),
        debug = QuestionDebug(
            mode = QuestionRequestDebugMode.STUB,
            stub = QuestionRequestDebugStubs.SUCCESS
        )
    )

    private val appSettings: IQuizQuestionAppSettings = object : IQuizQuestionAppSettings {
        override val corSettings: QuizQuestionCorSettings = QuizQuestionCorSettings()
        override val processor: QuizQuestionProcessor = QuizQuestionProcessor(corSettings)
    }

    class TestApplicationCall(private val request: IRequestQuestion) {
        var res: IResponseQuestion? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : IRequestQuestion> receive(): T = request as T
        fun respond(res: IResponseQuestion) {
            this.res = res
        }
    }

    private suspend fun TestApplicationCall.createKtor(appSettings: IQuizQuestionAppSettings) {
        val resp = appSettings.controllerHelper(
            { fromTransport(receive<QuestionCreateRequest>()) },
            { toTransport() },
            this::class,
            "createKtor"
        )
        respond(resp)
    }

    @Test
    fun ktorHelperTest() = runTest {
        val testApp = TestApplicationCall(request).apply { createKtor(appSettings) }
        val res = testApp.res as QuestionCreateResponse
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}
