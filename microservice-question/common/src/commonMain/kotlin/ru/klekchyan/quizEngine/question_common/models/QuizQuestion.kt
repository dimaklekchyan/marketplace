package ru.klekchyan.quizEngine.question_common.models

import models.QuizCommonEntityId

data class QuizQuestion(
    var id: QuizCommonEntityId = QuizCommonEntityId.NONE,
    var roundId: QuizCommonEntityId = QuizCommonEntityId.NONE,
    var gameId: QuizCommonEntityId = QuizCommonEntityId.NONE,
    var type: QuizQuestionType = QuizQuestionType.NONE,
    var formulation: String = "",
    val answers: MutableList<QuizQuestionAnswer> = mutableListOf(),
    val matchingTerms: MutableList<QuizQuestionMatchingTerm> = mutableListOf(),
)