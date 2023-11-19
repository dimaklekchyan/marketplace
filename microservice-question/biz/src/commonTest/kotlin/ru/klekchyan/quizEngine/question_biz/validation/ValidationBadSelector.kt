package ru.klekchyan.quizEngine.question_biz.validation

import kotlinx.coroutines.test.runTest
import models.QuizCommonEntityId
import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.*
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationSelectorCorrect(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionsSelectorRequest = QuizQuestionsSelector(
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123")
        )
    )

    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(QuizQuestionState.FAILING, ctx.state)
}

fun validationSelectorGameIdEmpty(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionsSelectorRequest = QuizQuestionsSelector(
            gameId = QuizCommonEntityId(""),
            roundId = QuizCommonEntityId("123")
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("gameId", error?.field)
    assertContains(error?.message ?: "", "gameId")
}

fun validationSelectorGameIdBadFormat(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionsSelectorRequest = QuizQuestionsSelector(
            gameId = QuizCommonEntityId("_+()23"),
            roundId = QuizCommonEntityId("123")
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("gameId", error?.field)
    assertContains(error?.message ?: "", "gameId")
}

fun validationSelectorRoundIdEmpty(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionsSelectorRequest = QuizQuestionsSelector(
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("")
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("roundId", error?.field)
    assertContains(error?.message ?: "", "roundId")
}

fun validationSelectorRoundIdBadFormat(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionsSelectorRequest = QuizQuestionsSelector(
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("_+()23")
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("roundId", error?.field)
    assertContains(error?.message ?: "", "roundId")
}