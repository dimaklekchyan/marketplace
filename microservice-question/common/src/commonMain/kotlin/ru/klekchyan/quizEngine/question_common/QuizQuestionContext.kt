package ru.klekchyan.quizEngine.question_common

import NONE
import kotlinx.datetime.Instant
import models.QuizError
import models.QuizRequestId
import ru.klekchyan.quizEngine.question_common.models.*
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs

data class QuizQuestionContext(
    var command: QuizQuestionCommand = QuizQuestionCommand.NONE,
    var state: QuizQuestionState = QuizQuestionState.NONE,
    val errors: MutableList<QuizError> = mutableListOf(),

    var workMode: QuizQuestionWorkMode = QuizQuestionWorkMode.PROD,
    var stubCase: QuizQuestionStubs = QuizQuestionStubs.NONE,

    var requestId: QuizRequestId = QuizRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var questionRequest: QuizQuestion = QuizQuestion(),
    var questionResponse: QuizQuestion = QuizQuestion(),
    var questionsSelectorRequest: QuizQuestionsSelector = QuizQuestionsSelector(),
    var questionsResponse: MutableList<QuizQuestion> = mutableListOf(),
)
