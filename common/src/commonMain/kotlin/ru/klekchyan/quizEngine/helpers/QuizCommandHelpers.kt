package ru.klekchyan.quizEngine.helpers

import ru.klekchyan.quizEngine.QuizContext
import ru.klekchyan.quizEngine.models.QuizCommand

fun QuizContext.isUpdatableCommand() =
    this.command in listOf(QuizCommand.CREATE, QuizCommand.UPDATE, QuizCommand.DELETE)
