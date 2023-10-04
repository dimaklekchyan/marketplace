package ru.klekchyan.quizEngine.models

import models.QuizCommonEntityId

data class QuizQuestionsSelector(
    val gameId: QuizCommonEntityId = QuizCommonEntityId.NONE,
    val roundId: QuizCommonEntityId = QuizCommonEntityId.NONE
)
