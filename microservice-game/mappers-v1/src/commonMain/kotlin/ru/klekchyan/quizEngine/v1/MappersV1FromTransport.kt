package ru.klekchyan.quizEngine.v1

import ru.klekchyan.quizEngine.QuizGameContext
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.requestId
import ru.klekchyan.quizEngine.stubs.QuizGameStubs
import ru.klekchyan.quizEngine.toId
import ru.klekchyan.quizEngine.v1.exceptions.UnknownGameRequestClass

fun QuizGameContext.fromTransport(request: IRequestGame) = when (request) {
    is GameCreateRequest -> fromTransport(request)
    is GameReadRequest -> fromTransport(request)
    is GameUpdateRequest -> fromTransport(request)
    is GameDeleteRequest -> fromTransport(request)
    is GameReadAllRequest -> fromTransport(request)
    else -> throw UnknownGameRequestClass(request::class)
}

private fun String?.toGameWithId() = QuizGame(id = this.toId())

private fun GameRequestDebugMode?.transportToWorkMode(): QuizGameWorkMode = when (this) {
    GameRequestDebugMode.PROD -> QuizGameWorkMode.PROD
    GameRequestDebugMode.TEST -> QuizGameWorkMode.TEST
    GameRequestDebugMode.STUB -> QuizGameWorkMode.STUB
    else -> QuizGameWorkMode.PROD
}

private fun GameRequestDebugStubs?.transportToStubCase(): QuizGameStubs = when (this) {
    GameRequestDebugStubs.SUCCESS -> QuizGameStubs.SUCCESS
    GameRequestDebugStubs.NOT_FOUND -> QuizGameStubs.NOT_FOUND
    GameRequestDebugStubs.BAD_ID -> QuizGameStubs.BAD_ID
    GameRequestDebugStubs.BAD_TITLE -> QuizGameStubs.BAD_TITLE
    else -> QuizGameStubs.NONE
}

fun QuizGameContext.fromTransport(request: GameCreateRequest) {
    command = QuizGameCommand.CREATE
    requestId = request.requestId()
    gameRequest = request.game?.toInternal() ?: QuizGame()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizGameContext.fromTransport(request: GameReadRequest) {
    command = QuizGameCommand.READ
    requestId = request.requestId()
    gameRequest = request.game?.id.toGameWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizGameContext.fromTransport(request: GameUpdateRequest) {
    command = QuizGameCommand.UPDATE
    requestId = request.requestId()
    gameRequest = request.game?.toInternal() ?: QuizGame()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizGameContext.fromTransport(request: GameDeleteRequest) {
    command = QuizGameCommand.DELETE
    requestId = request.requestId()
    gameRequest = request.game?.id.toGameWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizGameContext.fromTransport(request: GameReadAllRequest) {
    command = QuizGameCommand.READ_ALL
    requestId = request.requestId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

private fun GameCreateObject.toInternal() = QuizGame(
    title = title ?: "",
    description = description ?: ""
)

private fun GameUpdateObject.toInternal() = QuizGame(
    id = id.toId(),
    title = title ?: "",
    description = description ?: ""
)