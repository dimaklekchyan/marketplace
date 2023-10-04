package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.models.QuizGameCommand
import ru.klekchyan.quizEngine.models.QuizGameWorkMode

class QuizGameProcessor {
    suspend fun exec(ctx: QuizGameContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == QuizGameWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            QuizGameCommand.CREATE,
            QuizGameCommand.READ,
            QuizGameCommand.UPDATE,
            QuizGameCommand.DELETE -> {
                ctx.gameResponse = QuizGameStub.get()
            }
            QuizGameCommand.READ_ALL -> {
                val games = QuizGameStub.prepareGamesList()
                ctx.gamesResponse.addAll(games)
            }
            else -> {
                throw IllegalArgumentException("Not yet implemented context command: ${ctx.command}")
            }
        }
    }
}
