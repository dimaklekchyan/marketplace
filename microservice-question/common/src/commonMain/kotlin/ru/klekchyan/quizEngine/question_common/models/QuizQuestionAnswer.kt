package ru.klekchyan.quizEngine.question_common.models

data class QuizQuestionAnswer(
    var formulation: String = "",
    var isRight: Boolean = false
)