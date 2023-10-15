package ru.klekchyan.quizEngine.question_common.helpers

import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand

fun QuizQuestionContext.isUpdatableCommand() =
    this.command in listOf(
        QuizQuestionCommand.CREATE,
        QuizQuestionCommand.UPDATE,
        QuizQuestionCommand.DELETE,
    )
