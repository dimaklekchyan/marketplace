package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.models.QuizRoundCommand
import ru.klekchyan.quizEngine.models.QuizRoundWorkMode

class QuizRoundProcessor {
    suspend fun exec(ctx: QuizRoundContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == QuizRoundWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            QuizRoundCommand.CREATE,
            QuizRoundCommand.READ,
            QuizRoundCommand.UPDATE,
            QuizRoundCommand.DELETE -> {
                ctx.roundResponse = QuizRoundStub.get()
            }
            QuizRoundCommand.READ_ALL -> {
                val rounds = QuizRoundStub.prepareRoundsList(
                    gameId = ctx.roundsSelectorRequest.gameId.asString(),
                )
                ctx.roundsResponse.addAll(rounds)
            }
            else -> {
                throw IllegalArgumentException("Not yet implemented context command: ${ctx.command}")
            }
        }
    }
}
