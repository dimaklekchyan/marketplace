package ru.klekchyan.quizEngine.models

data class QuizGame(
    var id: QuizGameId = QuizGameId.NONE,
    var title: String = "",
    var description: String = ""
)