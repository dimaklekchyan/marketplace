package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.models.*

object QuizQuestionStub {
    fun get(): QuizQuestion = QUESTION.copy()

    private val QUESTION: QuizQuestion
        get() = QuizQuestion(
            id = QuizQuestionId(123),
            roundId = QuizRoundId(555),
            gameId = QuizGameId(999),
            type = QuizQuestionType.OPEN_QUESTION,
            formulation = "What's the count of venus from the sun?"
        )

    fun prepareResult(block: QuizQuestion.() -> Unit): QuizQuestion = get().apply(block)

    fun prepareQuestionsList(gameId: Int, roundId: Int) = listOf(
        quizOpenQuestion(id = 1, gameId = gameId, roundId = roundId, formulation = "What .. ?"),
        quizMultipleOptionsQuestions(id = 2, gameId = gameId, roundId = roundId, formulation = "Where .. ?"),
        quizMatchingTermsQuestion(id = 3, gameId = gameId,roundId =  roundId, formulation = "When .. ?"),
    )

    private fun quizOpenQuestion(id: Int, gameId: Int, roundId: Int, formulation: String,) =
        quizQuestion(
            base = QUESTION,
            id = id,
            gameId = gameId,
            roundId = roundId,
            formulation = formulation,
            type = QuizQuestionType.OPEN_QUESTION
        )

    private fun quizMultipleOptionsQuestions(id: Int, gameId: Int, roundId: Int, formulation: String,) =
        quizQuestion(
            base = QUESTION,
            id = id,
            gameId = gameId,
            roundId = roundId,
            formulation = formulation,
            type = QuizQuestionType.MULTIPLE_OPTIONS_QUESTION,
            answers = listOf(
                QuizQuestionAnswer(formulation = "1", isRight = false),
                QuizQuestionAnswer(formulation = "2", isRight = true),
                QuizQuestionAnswer(formulation = "3", isRight = false),
            )
        )

    private fun quizMatchingTermsQuestion(id: Int, gameId: Int, roundId: Int, formulation: String) =
        quizQuestion(
            base = QUESTION,
            id = id,
            gameId = gameId,
            roundId = roundId,
            formulation = formulation,
            type = QuizQuestionType.MATCHING_TERMS_QUESTION,
            matchingTerm = listOf(
                QuizQuestionMatchingTerm(term = "1", definition = "First"),
                QuizQuestionMatchingTerm(term = "2", definition = "Second"),
            )
        )

    private fun quizQuestion(
        base: QuizQuestion,
        id: Int,
        gameId: Int,
        roundId: Int,
        formulation: String,
        type: QuizQuestionType,
        answers: List<QuizQuestionAnswer> = emptyList(),
        matchingTerm: List<QuizQuestionMatchingTerm> = emptyList()
    ) = base.copy(
        id = QuizQuestionId(id),
        gameId = QuizGameId(gameId),
        roundId = QuizRoundId(roundId),
        formulation = formulation,
        type = type,
        answers = answers.toMutableList(),
        matchingTerms = matchingTerm.toMutableList()
    )
}
