package ru.klekchyan.quizEngine

import kotlinx.datetime.Instant
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.stubs.QuizStubs

data class QuizContext(
    var command: QuizCommand = QuizCommand.NONE,
    var state: QuizState = QuizState.NONE,
    val errors: MutableList<QuizError> = mutableListOf(),

    var workMode: QuizWorkMode = QuizWorkMode.PROD,
    var stubCase: QuizStubs = QuizStubs.NONE,

    var requestId: QuizRequestId = QuizRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var gameRequest: QuizGame = QuizGame(),
    var gameResponse: QuizGame = QuizGame(),
    var gamesSelectorRequest: QuizGamesSelector = QuizGamesSelector(),
    var gamesResponse: MutableList<QuizGame> = mutableListOf(),

    var roundRequest: QuizRound = QuizRound(),
    var roundResponse: QuizRound = QuizRound(),
    var roundsSelectorRequest: QuizRoundsSelector = QuizRoundsSelector(),
    var roundsResponse: MutableList<QuizRound> = mutableListOf(),

    var questionRequest: QuizQuestion = QuizQuestion(),
    var questionResponse: QuizQuestion = QuizQuestion(),
    var questionsSelectorRequest: QuizQuestionsSelector = QuizQuestionsSelector(),
    var questionsResponse: MutableList<QuizQuestion> = mutableListOf(),
)
