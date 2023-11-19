package ru.klekchyan.quizEngine.question_mappers_log

import models.QuizCommonEntityId
import models.QuizError
import models.QuizRequestId
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestion
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionType
import ru.klekchyan.quizEngine.quiestion_mappers_log.toLog
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {

    @Test
    fun fromContext() {
        val context = QuizQuestionContext(
            requestId = QuizRequestId("1234"),
            command = QuizQuestionCommand.CREATE,
            questionResponse = QuizQuestion(
                id = QuizCommonEntityId("1"),
                gameId = QuizCommonEntityId("12"),
                roundId = QuizCommonEntityId("123"),
                type = QuizQuestionType.OPEN_QUESTION,
                formulation = "Bla bla bla"
            ),
            errors = mutableListOf(
                QuizError(
                    code = "err",
                    group = "request",
                    field = "formulation",
                    message = "wrong formulation",
                )
            ),
            state = QuizQuestionState.RUNNING,
        )

        val log = context.toLog("test-id")

        assertEquals("test-id", log.logId)
        assertEquals("microservice-question", log.source)
        assertEquals("1234", log.requestId)
        assertEquals("OPEN_QUESTION", log.responseQuestion?.questionType)
        val error = log.errors?.firstOrNull()
        assertEquals("wrong formulation", error?.message)
        assertEquals("ERROR", error?.level)
    }
}
