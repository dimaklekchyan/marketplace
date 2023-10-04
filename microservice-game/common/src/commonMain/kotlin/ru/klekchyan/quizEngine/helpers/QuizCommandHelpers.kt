package ru.klekchyan.quizEngine.helpers

import ru.klekchyan.quizEngine.QuizGameContext
import ru.klekchyan.quizEngine.models.QuizGameCommand

fun QuizGameContext.isUpdatableCommand() =
    this.command in listOf(
        QuizGameCommand.CREATE,
        QuizGameCommand.UPDATE,
        QuizGameCommand.DELETE,
    )
