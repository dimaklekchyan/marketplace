package ru.klekchyan.quizEngine

import models.QuizCommonEntityId
import ru.klekchyan.quizEngine.models.*

object QuizRoundStub {
    fun get(): QuizRound = round.copy()

    private val round: QuizRound
        get() = QuizRound(
            id = QuizCommonEntityId("123"),
            gameId = QuizCommonEntityId("999"),
            title = "Round 1",
            description = "Description of first round"
        )

    fun prepareResult(block: QuizRound.() -> Unit): QuizRound = get().apply(block)

    fun prepareRoundsList(gameId: String) = listOf(
        quizRound(round, id = "1", gameId = gameId, title = "Round 1", description = "Description of first round"),
        quizRound(round, id = "2", gameId = gameId, title = "Round 2", description = "Description of second round"),
        quizRound(round, id = "3", gameId = gameId, title = "Round 3", description = "Description of third round"),
    )

    private fun quizRound(
        base: QuizRound,
        id: String,
        gameId: String,
        title: String,
        description: String
    ) = base.copy(
        id = QuizCommonEntityId(id),
        gameId = QuizCommonEntityId(gameId),
        title = title,
        description = description
    )
}
