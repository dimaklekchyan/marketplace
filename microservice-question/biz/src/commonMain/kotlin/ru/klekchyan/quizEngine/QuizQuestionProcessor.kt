package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.models.QuizQuestionCommand
import ru.klekchyan.quizEngine.models.QuizQuestionWorkMode

class QuizQuestionProcessor {
    suspend fun exec(ctx: QuizQuestionContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == QuizQuestionWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            QuizQuestionCommand.CREATE,
            QuizQuestionCommand.READ,
            QuizQuestionCommand.UPDATE,
            QuizQuestionCommand.DELETE -> {
                ctx.questionResponse = QuizQuestionStub.get()
            }
            QuizQuestionCommand.READ_ALL -> {
                val questions = QuizQuestionStub.prepareQuestionsList(
                    gameId = ctx.questionsSelectorRequest.gameId.asString(),
                    roundId = ctx.questionsSelectorRequest.roundId.asString()
                )
                ctx.questionsResponse.addAll(questions)
            }
            else -> {
                throw IllegalArgumentException("Not yet implemented context command: ${ctx.command}")
            }
        }
    }
}
