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

fun validationFormulationCorrect(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
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

fun validationFormulationTrim(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
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
            formulation = "\n\t How to ...? \n\t"
        )
    )

    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(QuizQuestionState.FAILING, ctx.state)
    assertEquals("How to ...?", ctx.questionValidated.formulation)
}

fun validationFormulationEmpty(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
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
            formulation = ""
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("formulation", error?.field)
    assertContains(error?.message ?: "", "formulation")
}