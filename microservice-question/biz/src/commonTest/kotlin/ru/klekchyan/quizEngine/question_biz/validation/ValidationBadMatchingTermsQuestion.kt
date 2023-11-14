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

fun validationMatchingTermsQuestionCorrect(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MATCHING_TERMS_QUESTION,
            formulation = "How to ...?",
            matchingTerms = mutableListOf(
                QuizQuestionMatchingTerm(term = "Term 1", definition = "Definition 1"),
                QuizQuestionMatchingTerm(term = "Term 2", definition = "Definition 2"),
                QuizQuestionMatchingTerm(term = "Term 3", definition = "Definition 3"),
            )
        )
    )

    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(QuizQuestionState.FAILING, ctx.state)
}

fun validationMatchingTermsQuestionWithoutTerms(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MATCHING_TERMS_QUESTION,
            formulation = "How to ...?",
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("matchingTerms", error?.field)
    assertContains(error?.message ?: "", "matchingTerms")
}

fun validationMatchingTermsQuestionWithAnswers(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MATCHING_TERMS_QUESTION,
            formulation = "How to ...?",
            matchingTerms = mutableListOf(
                QuizQuestionMatchingTerm(term = "Term 1", definition = "Definition 1"),
                QuizQuestionMatchingTerm(term = "Term 2", definition = "Definition 2"),
                QuizQuestionMatchingTerm(term = "Term 3", definition = "Definition 3"),
            ),
            answers = mutableListOf(
                QuizQuestionAnswer(formulation = "Answer 1", isRight = true),
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

fun validationMatchingTermsQuestionEmptyTerm(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MATCHING_TERMS_QUESTION,
            formulation = "How to ...?",
            matchingTerms = mutableListOf(
                QuizQuestionMatchingTerm(term = "", definition = "Definition 1"),
                QuizQuestionMatchingTerm(term = "Term 2", definition = "Definition 2"),
                QuizQuestionMatchingTerm(term = "Term 3", definition = "Definition 3"),
            ),
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("matching term", error?.field)
    assertContains(error?.message ?: "", "matching term")
}

fun validationMatchingTermsQuestionEmptyDefinition(command: QuizQuestionCommand, processor: QuizQuestionProcessor) = runTest {
    val ctx = QuizQuestionContext(
        command = command,
        state = QuizQuestionState.NONE,
        workMode = QuizQuestionWorkMode.PROD,
        stubCase = QuizQuestionStubs.SUCCESS,
        questionRequest = QuizQuestion(
            id = QuizCommonEntityId("1"),
            gameId = QuizCommonEntityId("12"),
            roundId = QuizCommonEntityId("123"),
            type = QuizQuestionType.MATCHING_TERMS_QUESTION,
            formulation = "How to ...?",
            matchingTerms = mutableListOf(
                QuizQuestionMatchingTerm(term = "Term 1", definition = ""),
                QuizQuestionMatchingTerm(term = "Term 2", definition = "Definition 2"),
                QuizQuestionMatchingTerm(term = "Term 3", definition = "Definition 3"),
            ),
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(QuizQuestionState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("matching term definition", error?.field)
    assertContains(error?.message ?: "", "matching term definition")
}