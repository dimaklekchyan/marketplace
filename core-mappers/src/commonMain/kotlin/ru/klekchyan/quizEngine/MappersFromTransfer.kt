package ru.klekchyan.quizEngine

import models.QuizCommonEntityId
import models.QuizRequestId
import ru.klekchyan.quizEngine.api.v1.models.IRequest

fun IRequest?.requestId(): QuizRequestId = this?.requestId?.let { QuizRequestId(it) } ?: QuizRequestId.NONE

fun String?.toId() = this?.let { QuizCommonEntityId(it) } ?: QuizCommonEntityId.NONE