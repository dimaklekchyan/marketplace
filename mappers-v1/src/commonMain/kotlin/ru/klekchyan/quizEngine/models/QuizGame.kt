package ru.klekchyan.quizEngine.models

data class QuizGame(
    val id: QuizGameId = QuizGameId.NONE,
    val title: String = "",
    val description: String = ""
)
