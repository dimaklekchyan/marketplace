package ru.klekchyan.quizEngine.helpers

import ru.klekchyan.quizEngine.QuizRoundContext
import ru.klekchyan.quizEngine.models.QuizRoundCommand

fun QuizRoundContext.isUpdatableCommand() =
    this.command in listOf(
        QuizRoundCommand.CREATE,
        QuizRoundCommand.UPDATE,
        QuizRoundCommand.DELETE,
    )
