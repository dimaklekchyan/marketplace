package ru.klekchyan.quizEngine.models

import models.QuizCommonEntityId

data class QuizRoundsSelector(
    val gameId: QuizCommonEntityId = QuizCommonEntityId.NONE
)
