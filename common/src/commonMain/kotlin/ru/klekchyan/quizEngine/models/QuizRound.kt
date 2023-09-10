package ru.klekchyan.quizEngine.models

data class QuizRound(
    var id: QuizRoundId = QuizRoundId.NONE,
    var gameId: QuizGameId = QuizGameId.NONE,
    var title: String = "",
    var description: String = ""
)