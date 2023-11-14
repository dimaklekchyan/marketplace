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

fun validationMultipleOptionsQuestionCorrect(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MULTIPLE_OPTIONS_QUESTION,
            formulation = "How to ...?",
            answers = mutableListOf(
                QuizQuestionAnswer(formulation = "Answer 1", isRight = true),
                QuizQuestionAnswer(formulation = "Answer 2", isRight = false),
                QuizQuestionAnswer(formulation = "Answer 3", isRight = false),
            )
        )
    )

    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(QuizQuestionState.FAILING, ctx.state)
}

fun validationMultipleOptionsQuestionWithTerms(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MULTIPLE_OPTIONS_QUESTION,
            formulation = "How to ...?",
            answers = mutableListOf(
                QuizQuestionAnswer(formulation = "Answer 1", isRight = true),
                QuizQuestionAnswer(formulation = "Answer 2", isRight = false),
                QuizQuestionAnswer(formulation = "Answer 3", isRight = false),
            ),
            matchingTerms = mutableListOf(
                QuizQuestionMatchingTerm(term = "Term 1", definition = "Definition 1")
            )
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("matchingTerms", error?.field)
    assertContains(error?.message ?: "", "matchingTerms")
}

fun validationMultipleOptionsQuestionWithoutRightAnswer(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MULTIPLE_OPTIONS_QUESTION,
            formulation = "How to ...?",
            answers = mutableListOf(
                QuizQuestionAnswer(formulation = "Answer 1", isRight = false),
                QuizQuestionAnswer(formulation = "Answer 2", isRight = false),
                QuizQuestionAnswer(formulation = "Answer 3", isRight = false),
            ),
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("answers", error?.field)
    assertContains(error?.message ?: "", "answers")
}

fun validationMultipleOptionsQuestionWithoutAnswers(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MULTIPLE_OPTIONS_QUESTION,
            formulation = "How to ...?"
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("answers", error?.field)
    assertContains(error?.message ?: "", "answers")
}

fun validationMultipleOptionsQuestionWithSeveralRightAnswers(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MULTIPLE_OPTIONS_QUESTION,
            formulation = "How to ...?",
            answers = mutableListOf(
                QuizQuestionAnswer(formulation = "Answer 1", isRight = true),
                QuizQuestionAnswer(formulation = "Answer 2", isRight = true),
                QuizQuestionAnswer(formulation = "Answer 3", isRight = false),
            ),
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("answers", error?.field)
    assertContains(error?.message ?: "", "answers")
}

fun validationMultipleOptionsQuestionAnswersFormulation(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MULTIPLE_OPTIONS_QUESTION,
            formulation = "How to ...?",
            answers = mutableListOf(
                QuizQuestionAnswer(formulation = "", isRight = true),
                QuizQuestionAnswer(formulation = "Answer 2", isRight = false),
                QuizQuestionAnswer(formulation = "Answer 3", isRight = false),
            ),
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("answer formulation", error?.field)
    assertContains(error?.message ?: "", "answer formulation")
}