package ru.klekchyan.quizEngine.models

import models.QuizCommonEntityId

data class QuizRound(
    val id: QuizCommonEntityId = QuizCommonEntityId.NONE,
    val gameId: QuizCommonEntityId = QuizCommonEntityId.NONE,
    val title: String = "",
    val description: String = ""
)
