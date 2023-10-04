package ru.klekchyan.quizEngine.v1

import ru.klekchyan.quizEngine.QuizRoundContext
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.requestId
import ru.klekchyan.quizEngine.stubs.QuizRoundStubs
import ru.klekchyan.quizEngine.toId
import ru.klekchyan.quizEngine.v1.exceptions.UnknownRoundRequestClass

fun QuizRoundContext.fromTransport(request: IRequestRound) = when (request) {
    is RoundCreateRequest -> fromTransport(request)
    is RoundReadRequest -> fromTransport(request)
    is RoundUpdateRequest -> fromTransport(request)
    is RoundDeleteRequest -> fromTransport(request)
    is RoundReadAllRequest -> fromTransport(request)
    else -> throw UnknownRoundRequestClass(request::class)
}

private fun String?.toRoundWithId() = QuizRound(id = this.toId())

private fun RoundRequestDebugMode?.transportToWorkMode(): QuizRoundWorkMode = when (this) {
    RoundRequestDebugMode.PROD -> QuizRoundWorkMode.PROD
    RoundRequestDebugMode.TEST -> QuizRoundWorkMode.TEST
    RoundRequestDebugMode.STUB -> QuizRoundWorkMode.STUB
    else -> QuizRoundWorkMode.PROD
}

private fun RoundRequestDebugStubs?.transportToStubCase(): QuizRoundStubs = when (this) {
    RoundRequestDebugStubs.SUCCESS -> QuizRoundStubs.SUCCESS
    RoundRequestDebugStubs.NOT_FOUND -> QuizRoundStubs.NOT_FOUND
    RoundRequestDebugStubs.BAD_ID -> QuizRoundStubs.BAD_ID
    RoundRequestDebugStubs.BAD_TITLE -> QuizRoundStubs.BAD_TITLE
    else -> QuizRoundStubs.NONE
}

fun QuizRoundContext.fromTransport(request: RoundCreateRequest) {
    command = QuizRoundCommand.CREATE
    requestId = request.requestId()
    roundRequest = request.round?.toInternal() ?: QuizRound()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizRoundContext.fromTransport(request: RoundReadRequest) {
    command = QuizRoundCommand.READ
    requestId = request.requestId()
    roundRequest = request.round?.id.toRoundWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizRoundContext.fromTransport(request: RoundUpdateRequest) {
    command = QuizRoundCommand.UPDATE
    requestId = request.requestId()
    roundRequest = request.round?.toInternal() ?: QuizRound()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizRoundContext.fromTransport(request: RoundDeleteRequest) {
    command = QuizRoundCommand.DELETE
    requestId = request.requestId()
    roundRequest = request.round?.id.toRoundWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizRoundContext.fromTransport(request: RoundReadAllRequest) {
    command = QuizRoundCommand.READ_ALL
    requestId = request.requestId()
    roundsSelectorRequest = request.selector?.toInternal() ?: QuizRoundsSelector()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

private fun RoundCreateObject.toInternal() = QuizRound(
    gameId = gameId.toId(),
    title = title ?: "",
    description = description ?: ""
)

private fun RoundUpdateObject.toInternal() = QuizRound(
    id = id.toId(),
    gameId = gameId.toId(),
    title = title ?: "",
    description = description ?: ""
)

private fun RoundsSelector.toInternal() = QuizRoundsSelector(
    gameId = gameId.toId()
)