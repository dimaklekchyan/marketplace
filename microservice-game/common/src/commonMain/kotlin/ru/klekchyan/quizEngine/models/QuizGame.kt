package ru.klekchyan.quizEngine.models

import models.QuizCommonEntityId

data class QuizGame(
    val id: QuizCommonEntityId = QuizCommonEntityId.NONE,
    val title: String = "",
    val description: String = ""
)
