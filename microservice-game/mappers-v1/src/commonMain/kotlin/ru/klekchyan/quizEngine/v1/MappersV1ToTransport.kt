package ru.klekchyan.quizEngine.v1

import models.QuizCommonEntityId
import models.QuizError
import ru.klekchyan.quizEngine.QuizGameContext
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.v1.exceptions.UnknownQuizGameCommand

fun QuizGameContext.toTransport(): IResponseGame = when (val cmd = command) {
    QuizGameCommand.CREATE -> toTransportCreateGame()
    QuizGameCommand.READ -> toTransportReadGame()
    QuizGameCommand.UPDATE -> toTransportUpdateGame()
    QuizGameCommand.DELETE -> toTransportDeleteGame()
    QuizGameCommand.READ_ALL -> toTransportReadAllGames()
    QuizGameCommand.NONE -> throw UnknownQuizGameCommand(cmd)
}

fun QuizGameContext.toTransportCreateGame() = GameCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizGameState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    game = gameResponse.toTransport()
)

fun QuizGameContext.toTransportReadGame() = GameReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizGameState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    game = gameResponse.toTransport()
)

fun QuizGameContext.toTransportUpdateGame() = GameUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizGameState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    game = gameResponse.toTransport()
)

fun QuizGameContext.toTransportDeleteGame() = GameDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizGameState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    game = gameResponse.toTransport()
)

fun QuizGameContext.toTransportReadAllGames() = GameReadAllResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizGameState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    games = gamesResponse.map { it.toTransport() }
)

private fun QuizGame.toTransport() = GameResponseObject(
    id = id.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
    title = title,
    description = description
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