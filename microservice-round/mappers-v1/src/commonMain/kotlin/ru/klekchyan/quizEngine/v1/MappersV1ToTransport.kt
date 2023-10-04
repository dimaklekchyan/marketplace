package ru.klekchyan.quizEngine.v1

import models.QuizCommonEntityId
import models.QuizError
import ru.klekchyan.quizEngine.QuizRoundContext
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.v1.exceptions.UnknownQuizRoundCommand

fun QuizRoundContext.toTransport(): IResponseRound = when (val cmd = command) {
    QuizRoundCommand.CREATE -> toTransportCreateRound()
    QuizRoundCommand.READ -> toTransportReadRound()
    QuizRoundCommand.UPDATE -> toTransportUpdateRound()
    QuizRoundCommand.DELETE -> toTransportDeleteRound()
    QuizRoundCommand.READ_ALL -> toTransportReadAllRounds()
    QuizRoundCommand.NONE -> throw UnknownQuizRoundCommand(cmd)
}

fun QuizRoundContext.toTransportCreateRound() = RoundCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizRoundState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    round = roundResponse.toTransport()
)

fun QuizRoundContext.toTransportReadRound() = RoundReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizRoundState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    round = roundResponse.toTransport()
)

fun QuizRoundContext.toTransportUpdateRound() = RoundUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizRoundState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    round = roundResponse.toTransport()
)

fun QuizRoundContext.toTransportDeleteRound() = RoundDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizRoundState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    round = roundResponse.toTransport()
)

fun QuizRoundContext.toTransportReadAllRounds() = RoundReadAllResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizRoundState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    rounds = roundsResponse.map { it.toTransport() }
)

private fun QuizRound.toTransport() = RoundResponseObject(
    id = id.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
    gameId = gameId.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
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