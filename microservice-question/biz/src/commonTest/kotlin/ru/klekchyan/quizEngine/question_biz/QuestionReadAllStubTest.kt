package ru.klekchyan.quizEngine.question_biz

import kotlinx.coroutines.test.runTest
import models.QuizCommonEntityId
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.*
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class QuestionReadAllStubTest {

    private val processor = QuizQuestionProcessor()

    private val id = QuizCommonEntityId("1")
    private val gameId = QuizCommonEntityId("12")
    private val roundId = QuizCommonEntityId("123")

    @Test
    fun success() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.READ,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.SUCCESS,
            questionRequest = QuizQuestion(
                id = id
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FINISHING, ctx.state)
        assertEquals(id, ctx.questionResponse.id)
    }

    @Test
    fun badGameId() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.BAD_GAME_ID,
            questionsSelectorRequest = QuizQuestionsSelector(
                gameId = QuizCommonEntityId.NONE,
                roundId = roundId
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FAILING, ctx.state)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("gameId", ctx.errors.firstOrNull()?.field)
    }

    @Test
    fun badRoundId() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.BAD_ROUND_ID,
            questionsSelectorRequest = QuizQuestionsSelector(
                gameId = gameId,
                roundId = QuizCommonEntityId.NONE
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FAILING, ctx.state)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("roundId", ctx.errors.firstOrNull()?.field)
    }

    @Test
    fun dbError() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.READ,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.DB_ERROR,
            questionRequest = QuizQuestion(
                id = id
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestion(), ctx.questionResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badStubCase() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.READ,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.BAD_FORMULATION,
            questionRequest = QuizQuestion(
                id = id
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestion(), ctx.questionResponse)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}