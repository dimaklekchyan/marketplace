package ru.klekchyan.quizEngine.question_biz

import kotlinx.coroutines.test.runTest
import models.QuizCommonEntityId
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.*
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class QuestionUpdateStubTest {

    private val processor = QuizQuestionProcessor()

    private val id = QuizCommonEntityId("1")
    private val gameId = QuizCommonEntityId("12")
    private val roundId = QuizCommonEntityId("123")
    private val type = QuizQuestionType.OPEN_QUESTION
    private val formulation = "Formulation ..."

    @Test
    fun success() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.SUCCESS,
            questionRequest = QuizQuestion(
                gameId = gameId,
                roundId = roundId,
                type = type,
                formulation = formulation
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FINISHING, ctx.state)
        assertEquals(gameId, ctx.questionResponse.gameId)
        assertEquals(roundId, ctx.questionResponse.roundId)
        assertEquals(formulation, ctx.questionResponse.formulation)
        assertEquals(type, ctx.questionResponse.type)
    }

    @Test
    fun badId() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.BAD_ID,
            questionRequest = QuizQuestion(
                id = QuizCommonEntityId.NONE,
                gameId = gameId,
                roundId = roundId,
                type = type,
                formulation = formulation
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FAILING, ctx.state)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
    }

    @Test
    fun badGameId() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.BAD_GAME_ID,
            questionRequest = QuizQuestion(
                gameId = QuizCommonEntityId.NONE,
                roundId = roundId,
                type = type,
                formulation = formulation
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
            questionRequest = QuizQuestion(
                gameId = gameId,
                roundId =  QuizCommonEntityId.NONE,
                type = type,
                formulation = formulation
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FAILING, ctx.state)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("roundId", ctx.errors.firstOrNull()?.field)
    }

    @Test
    fun badType() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.BAD_TYPE,
            questionRequest = QuizQuestion(
                gameId = gameId,
                roundId =  roundId,
                type = QuizQuestionType.NONE,
                formulation = formulation
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FAILING, ctx.state)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("type", ctx.errors.firstOrNull()?.field)
    }

    @Test
    fun badFormulation() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.BAD_FORMULATION,
            questionRequest = QuizQuestion(
                gameId = gameId,
                roundId =  roundId,
                type = type,
                formulation = ""
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FAILING, ctx.state)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("formulation", ctx.errors.firstOrNull()?.field)
    }

    @Test
    fun badAnswers() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.BAD_ANSWERS,
            questionRequest = QuizQuestion(
                gameId = gameId,
                roundId =  roundId,
                type = QuizQuestionType.MULTIPLE_OPTIONS_QUESTION,
                formulation = formulation,
                answers = emptyList<QuizQuestionAnswer>().toMutableList()
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FAILING, ctx.state)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("answers", ctx.errors.firstOrNull()?.field)
    }

    @Test
    fun badMatchingTerms() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.BAD_TERMS,
            questionRequest = QuizQuestion(
                gameId = gameId,
                roundId =  roundId,
                type = QuizQuestionType.MATCHING_TERMS_QUESTION,
                formulation = formulation,
                matchingTerms = emptyList<QuizQuestionMatchingTerm>().toMutableList()
            )
        )
        processor.exec(ctx)
        assertEquals(QuizQuestionState.FAILING, ctx.state)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
        assertEquals("matchingTerms", ctx.errors.firstOrNull()?.field)
    }

    @Test
    fun dbError() = runTest {
        val ctx = QuizQuestionContext(
            command = QuizQuestionCommand.UPDATE,
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
            command = QuizQuestionCommand.UPDATE,
            state = QuizQuestionState.NONE,
            workMode = QuizQuestionWorkMode.STUB,
            stubCase = QuizQuestionStubs.CANNOT_DELETE,
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