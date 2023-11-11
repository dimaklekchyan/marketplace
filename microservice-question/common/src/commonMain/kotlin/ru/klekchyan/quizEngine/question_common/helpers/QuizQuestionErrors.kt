package ru.klekchyan.quizEngine.question_common.helpers

import models.QuizError
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState

fun QuizQuestionContext.addError(vararg error: QuizError) = errors.addAll(error)

fun QuizQuestionContext.fail(error: QuizError) {
    addError(error)
    state = QuizQuestionState.FAILING
}

