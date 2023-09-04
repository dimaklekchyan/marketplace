package ru.klekchyan.quizEngine.models

data class QuizQuestion(
    var id: QuizQuestionId = QuizQuestionId.NONE,
    var roundId: QuizRoundId = QuizRoundId.NONE,
    var gameId: QuizGameId = QuizGameId.NONE,
    var type: QuizQuestionType = QuizQuestionType.NONE,
    var formulation: String = "",
    val answers: MutableList<QuizQuestionAnswer> = mutableListOf(),
    val matchingTerms: MutableList<QuizQuestionMatchingTerm> = mutableListOf(),
)