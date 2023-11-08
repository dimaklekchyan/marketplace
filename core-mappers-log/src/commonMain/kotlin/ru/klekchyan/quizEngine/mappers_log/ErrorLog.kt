package ru.klekchyan.quizEngine.mappers_log

import models.QuizError
import ru.klekchyan.quizEngine.api.v1.models.ErrorLogModel

fun QuizError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)