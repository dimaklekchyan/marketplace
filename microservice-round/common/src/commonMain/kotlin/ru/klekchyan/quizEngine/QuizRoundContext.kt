package ru.klekchyan.quizEngine

import NONE
import kotlinx.datetime.Instant
import models.QuizError
import models.QuizRequestId
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.stubs.QuizRoundStubs

data class QuizRoundContext(
    var command: QuizRoundCommand = QuizRoundCommand.NONE,
    var state: QuizRoundState = QuizRoundState.NONE,
    val errors: MutableList<QuizError> = mutableListOf(),

    var workMode: QuizRoundWorkMode = QuizRoundWorkMode.PROD,
    var stubCase: QuizRoundStubs = QuizRoundStubs.NONE,

    var requestId: QuizRequestId = QuizRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var roundRequest: QuizRound = QuizRound(),
    var roundResponse: QuizRound = QuizRound(),
    var roundsSelectorRequest: QuizRoundsSelector = QuizRoundsSelector(),
    var roundsResponse: MutableList<QuizRound> = mutableListOf(),
)
