package ru.klekchyan.quizEngine.v1

import ru.klekchyan.quizEngine.QuizContext
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.v1.exceptions.UnknownQuizCommand

fun QuizContext.toTransport(): IResponse = when (val cmd = command) {
    QuizCommand.CREATE_GAME -> toTransportCreateGame()
    QuizCommand.CREATE_ROUND -> toTransportCreateRound()
    QuizCommand.CREATE_QUESTION -> toTransportCreateQuestion()

    QuizCommand.READ_GAME -> toTransportReadGame()
    QuizCommand.READ_ROUND -> toTransportReadRound()
    QuizCommand.READ_QUESTION -> toTransportReadQuestion()

    QuizCommand.UPDATE_GAME -> toTransportUpdateGame()
    QuizCommand.UPDATE_ROUND -> toTransportUpdateRound()
    QuizCommand.UPDATE_QUESTION -> toTransportUpdateQuestion()

    QuizCommand.DELETE_GAME -> toTransportDeleteGame()
    QuizCommand.DELETE_ROUND -> toTransportDeleteRound()
    QuizCommand.DELETE_QUESTION -> toTransportDeleteQuestion()

    QuizCommand.READ_ALL_GAMES -> toTransportReadAllGames()
    QuizCommand.READ_ALL_ROUNDS -> toTransportReadAllRounds()
    QuizCommand.READ_ALL_QUESTIONS -> toTransportReadAllQuestions()

    QuizCommand.NONE -> throw UnknownQuizCommand(cmd)
}

fun QuizContext.toTransportCreateGame() = GameCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    game = gameResponse.toTransport()
)

fun QuizContext.toTransportCreateRound() = RoundCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    round = roundResponse.toTransport()
)

fun QuizContext.toTransportCreateQuestion() = QuestionCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    question = questionResponse.toTransport()
)

fun QuizContext.toTransportReadGame() = GameReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    game = gameResponse.toTransport()
)

fun QuizContext.toTransportReadRound() = RoundReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    round = roundResponse.toTransport()
)

fun QuizContext.toTransportReadQuestion() = QuestionReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    question = questionResponse.toTransport()
)

fun QuizContext.toTransportUpdateGame() = GameUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    game = gameResponse.toTransport()
)

fun QuizContext.toTransportUpdateRound() = RoundUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    round = roundResponse.toTransport()
)

fun QuizContext.toTransportUpdateQuestion() = QuestionUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    question = questionResponse.toTransport()
)

fun QuizContext.toTransportDeleteGame() = GameDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    game = gameResponse.toTransport()
)

fun QuizContext.toTransportDeleteRound() = RoundDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    round = roundResponse.toTransport()
)

fun QuizContext.toTransportDeleteQuestion() = QuestionDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    question = questionResponse.toTransport()
)

fun QuizContext.toTransportReadAllGames() = GameReadAllResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    games = gamesResponse.map { it.toTransport() }
)

fun QuizContext.toTransportReadAllRounds() = RoundReadAllResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    rounds = roundsResponse.map { it.toTransport() }
)

fun QuizContext.toTransportReadAllQuestions() = QuestionReadAllResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    questions = questionsResponse.map { it.toTransport() }
)

private fun QuizGame.toTransport() = GameResponseObject(
    id = id.takeIf { it != QuizGameId.NONE }?.asInt(),
    title = title,
    description = description,
)

private fun QuizRound.toTransport() = RoundResponseObject(
    id = id.takeIf { it != QuizRoundId.NONE }?.asInt(),
    gameId = gameId.takeIf { it != QuizGameId.NONE }?.asInt(),
    title = title,
    description = description
)

private fun QuizQuestion.toTransport() = QuestionResponseObject(
    id = id.takeIf { it != QuizQuestionId.NONE }?.asInt(),
    gameId = gameId.takeIf { it != QuizGameId.NONE }?.asInt(),
    roundId = roundId.takeIf { it != QuizRoundId.NONE }?.asInt(),
    questionType = type.toTransport(),
    formulation = formulation,
    answers = answers.map { it.toTransport() },
    matchingTerms = matchingTerms.map { it.toTransport() },
)

private fun QuizQuestionType.toTransport(): QuestionType? = when(this) {
    QuizQuestionType.OPEN_QUESTION -> QuestionType.OPEN_QUESTION
    QuizQuestionType.MULTIPLE_OPTIONS_QUESTION -> QuestionType.MULTIPLE_OPTIONS_QUESTION
    QuizQuestionType.MATCHING_TERMS_QUESTION -> QuestionType.MATCHING_TERMS_QUESTION
    QuizQuestionType.NONE -> null
}

private fun QuizQuestionAnswer.toTransport() = QuestionAnswer(
    formulation = formulation,
    isRight = isRight
)

private fun QuizQuestionMatchingTerm.toTransport() = QuestionMatchingTerm(
    term = term,
    definition = definition
)

private fun List<QuizError>.toTransport(): List<Error>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun QuizError.toTransport() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)