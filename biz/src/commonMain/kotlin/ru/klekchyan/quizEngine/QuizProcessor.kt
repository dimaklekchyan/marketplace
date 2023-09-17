package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.models.QuizCommand
import ru.klekchyan.quizEngine.models.QuizWorkMode

class QuizProcessor {
    suspend fun exec(ctx: QuizContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == QuizWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            QuizCommand.CREATE_QUESTION,
            QuizCommand.READ_QUESTION,
            QuizCommand.UPDATE_QUESTION,
            QuizCommand.DELETE_QUESTION -> {
                ctx.questionResponse = QuizQuestionStub.get()
            }
            QuizCommand.READ_ALL_QUESTIONS -> {
                ctx.questionsResponse.addAll(QuizQuestionStub.prepareQuestionsList(1, 1))
            }
            else -> {
                throw IllegalArgumentException("Not yet implemented context command: ${ctx.command}")
            }
        }
    }
}
