package ru.klekchyan.quizEngine.helpers

import ru.klekchyan.quizEngine.QuizContext
import ru.klekchyan.quizEngine.models.QuizError

fun Throwable.asQuizError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = QuizError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun QuizContext.addError(vararg error: QuizError) = errors.addAll(error)
