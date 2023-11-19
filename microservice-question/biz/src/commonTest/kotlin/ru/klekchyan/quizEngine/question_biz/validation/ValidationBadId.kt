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

fun validationIdCorrect(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.OPEN_QUESTION,
            formulation = "How to ...?"
        )
    )

    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(QuizQuestionState.FAILING, ctx.state)
}

fun validationIdEmpty(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId(""),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.OPEN_QUESTION,
            formulation = "How to ...?"
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationIdBadFormat(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("-+()231243"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.OPEN_QUESTION,
            formulation = "How to ...?"
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}