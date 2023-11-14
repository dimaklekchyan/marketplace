package ru.klekchyan.quizEngine.question_common.helpers

import models.QuizError
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState

fun QuizQuestionContext.addError(vararg error: QuizError) = errors.addAll(error)

fun QuizQuestionContext.fail(error: QuizError) {
    addError(error)
    state = QuizQuestionState.FAILING
}

fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
    level: QuizError.Level = QuizError.Level.ERROR,
) = QuizError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

