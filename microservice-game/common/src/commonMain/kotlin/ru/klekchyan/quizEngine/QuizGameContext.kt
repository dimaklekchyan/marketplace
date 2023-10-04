package ru.klekchyan.quizEngine

import NONE
import kotlinx.datetime.Instant
import models.QuizError
import models.QuizRequestId
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.stubs.QuizGameStubs

data class QuizGameContext(
    var command: QuizGameCommand = QuizGameCommand.NONE,
    var state: QuizGameState = QuizGameState.NONE,
    val errors: MutableList<QuizError> = mutableListOf(),

    var workMode: QuizGameWorkMode = QuizGameWorkMode.PROD,
    var stubCase: QuizGameStubs = QuizGameStubs.NONE,

    var requestId: QuizRequestId = QuizRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var gameRequest: QuizGame = QuizGame(),
    var gameResponse: QuizGame = QuizGame(),
    var gamesResponse: MutableList<QuizGame> = mutableListOf(),
)
