package ru.klekchyan.quizEngine

import models.QuizCommonEntityId
import models.QuizError
import models.QuizRequestId
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.*
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs
import ru.klekchyan.quizEngine.question_mappers.fromTransport
import ru.klekchyan.quizEngine.question_mappers.toTransport
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = QuestionCreateRequest(
            requestId = "1234",
            debug = QuestionDebug(
                mode = QuestionRequestDebugMode.STUB,
                stub = QuestionRequestDebugStubs.SUCCESS,
            ),
            question = QuestionCreateObject(
                gameId = "0",
                roundId = "0",
                questionType = QuestionType.OPEN_QUESTION,
                formulation = "Formulation"
            ),
        )

        val context = QuizQuestionContext()
        context.fromTransport(req)

        assertEquals(QuizQuestionStubs.SUCCESS, context.stubCase)
        assertEquals(QuizQuestionWorkMode.STUB, context.workMode)
        assertEquals("Formulation", context.questionRequest.formulation)
        assertEquals(QuizQuestionType.OPEN_QUESTION.name, context.questionRequest.type.name)
    }

    @Test
    fun toTransport() {
        val context = QuizQuestionContext(
            requestId = QuizRequestId("1234"),
            command = QuizQuestionCommand.CREATE,
            questionResponse = QuizQuestion(
                id = QuizCommonEntityId("1"),
                gameId = QuizCommonEntityId("0"),
                roundId = QuizCommonEntityId("0"),
                formulation = "Formulation",
                type = QuizQuestionType.OPEN_QUESTION
            ),
            errors = mutableListOf(
                QuizError(
                    code = "err",
                    group = "request",
                    field = "gameId",
                    message = "wrong game id",
                )
            ),
            state = QuizQuestionState.RUNNING,
        )

        val req = context.toTransport() as QuestionCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("Formulation", req.question?.formulation)
        assertEquals(QuestionType.OPEN_QUESTION.name, req.question?.questionType?.name)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("gameId", req.errors?.firstOrNull()?.field)
        assertEquals("wrong game id", req.errors?.firstOrNull()?.message)
    }
}