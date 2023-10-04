package ru.klekchyan.quizEngine.helpers

import ru.klekchyan.quizEngine.QuizQuestionContext
import ru.klekchyan.quizEngine.models.QuizQuestionCommand

fun QuizQuestionContext.isUpdatableCommand() =
    this.command in listOf(
        QuizQuestionCommand.CREATE,
        QuizQuestionCommand.UPDATE,
        QuizQuestionCommand.DELETE,
    )
