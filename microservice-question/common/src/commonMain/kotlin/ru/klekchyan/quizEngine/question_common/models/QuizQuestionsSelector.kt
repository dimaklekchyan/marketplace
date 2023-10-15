package ru.klekchyan.quizEngine.question_common.models

import models.QuizCommonEntityId

data class QuizQuestionsSelector(
    val gameId: QuizCommonEntityId = QuizCommonEntityId.NONE,
    val roundId: QuizCommonEntityId = QuizCommonEntityId.NONE
)
