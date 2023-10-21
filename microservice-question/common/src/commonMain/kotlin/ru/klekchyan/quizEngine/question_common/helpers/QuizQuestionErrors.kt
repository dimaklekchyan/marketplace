package ru.klekchyan.quizEngine.question_common.helpers

import models.QuizError
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext

fun QuizQuestionContext.addError(vararg error: QuizError) = errors.addAll(error)
