package ru.klekchyan.quizEngine.helpers

import ru.klekchyan.quizEngine.QuizContext
import ru.klekchyan.quizEngine.models.QuizCommand

fun QuizContext.isUpdatableCommand() =
    this.command in listOf(
        QuizCommand.CREATE_GAME,
        QuizCommand.CREATE_ROUND,
        QuizCommand.CREATE_QUESTION,
        QuizCommand.UPDATE_GAME,
        QuizCommand.UPDATE_ROUND,
        QuizCommand.UPDATE_QUESTION,
        QuizCommand.DELETE_GAME,
        QuizCommand.DELETE_ROUND,
        QuizCommand.DELETE_QUESTION
    )
