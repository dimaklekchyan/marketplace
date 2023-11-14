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
    var settings: QuizQuestionCorSettings = QuizQuestionCorSettings.NONE,

    var workMode: QuizQuestionWorkMode = QuizQuestionWorkMode.PROD,
    var stubCase: QuizQuestionStubs = QuizQuestionStubs.NONE,

    var requestId: QuizRequestId = QuizRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var questionRequest: QuizQuestion = QuizQuestion(),
    var questionsSelectorRequest: QuizQuestionsSelector = QuizQuestionsSelector(),

    var questionValidating: QuizQuestion = QuizQuestion(),
    var questionsSelectorValidating: QuizQuestionsSelector = QuizQuestionsSelector(),

    var questionValidated: QuizQuestion = QuizQuestion(),
    var questionsSelectorValidated: QuizQuestionsSelector = QuizQuestionsSelector(),

    var questionResponse: QuizQuestion = QuizQuestion(),
    var questionsResponse: MutableList<QuizQuestion> = mutableListOf(),
)
