package ru.klekchyan.quizEngine

import models.QuizCommonEntityId
import ru.klekchyan.quizEngine.models.*

object QuizGameStub {
    fun get(): QuizGame = game.copy()

    private val game: QuizGame
        get() = QuizGame(
            id = QuizCommonEntityId("123"),
            title = "Game 1",
            description = "Description of first Game"
        )

    fun prepareResult(block: QuizGame.() -> Unit): QuizGame = get().apply(block)

    fun prepareGamesList() = listOf(
        quizGame(game, id = "1", title = "Game 1", description = "Description of first Game"),
        quizGame(game, id = "2", title = "Game 2", description = "Description of second Game"),
        quizGame(game, id = "3", title = "Game 3", description = "Description of third Game"),
    )

    private fun quizGame(
        base: QuizGame,
        id: String,
        title: String,
        description: String
    ) = base.copy(
        id = QuizCommonEntityId(id),
        title = title,
        description = description
    )
}
