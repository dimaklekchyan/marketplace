package ru.klekchyan.quizEngine.v1

import ru.klekchyan.quizEngine.QuizContext
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.stubs.QuizStubs
import ru.klekchyan.quizEngine.v1.exceptions.UnknownRequestClass

fun QuizContext.fromTransport(request: IRequest) = when (request) {
    is GameCreateRequest -> fromTransport(request)
    is RoundCreateRequest -> fromTransport(request)
    is QuestionCreateRequest -> fromTransport(request)

    is GameReadRequest -> fromTransport(request)
    is RoundReadRequest -> fromTransport(request)
    is QuestionReadRequest -> fromTransport(request)

    is GameUpdateRequest -> fromTransport(request)
    is RoundUpdateRequest -> fromTransport(request)
    is QuestionUpdateRequest -> fromTransport(request)

    is GameDeleteRequest -> fromTransport(request)
    is RoundDeleteRequest -> fromTransport(request)
    is QuestionDeleteRequest -> fromTransport(request)

    is GameReadAllRequest -> fromTransport(request)
    is RoundReadAllRequest -> fromTransport(request)
    is QuestionReadAllRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request::class)
}

private fun IRequest?.requestId() = this?.requestId?.let { QuizRequestId(it) } ?: QuizRequestId.NONE

private fun Int?.toGameId() = this?.let { QuizGameId(it) } ?: QuizGameId.NONE
private fun Int?.toRoundId() = this?.let { QuizRoundId(it) } ?: QuizRoundId.NONE
private fun Int?.toQuestionId() = this?.let { QuizQuestionId(it) } ?: QuizQuestionId.NONE

private fun Int?.toGameWithId() = QuizGame(id = this.toGameId())
private fun Int?.toRoundWithId() = QuizRound(id = this.toRoundId())
private fun Int?.toQuestionWithId() = QuizQuestion(id = this.toQuestionId())

private fun RequestDebugMode?.transportToWorkMode(): QuizWorkMode = when (this) {
    RequestDebugMode.PROD -> QuizWorkMode.PROD
    RequestDebugMode.TEST -> QuizWorkMode.TEST
    RequestDebugMode.STUB -> QuizWorkMode.STUB
    else -> QuizWorkMode.PROD
}

private fun GameRequestDebugStubs?.transportToStubCase(): QuizStubs = when (this) {
    GameRequestDebugStubs.SUCCESS -> QuizStubs.SUCCESS
    GameRequestDebugStubs.NOT_FOUND -> QuizStubs.NOT_FOUND
    GameRequestDebugStubs.BAD_ID -> QuizStubs.BAD_ID
    else -> QuizStubs.NONE
}

private fun RoundRequestDebugStubs?.transportToStubCase(): QuizStubs = when (this) {
    RoundRequestDebugStubs.SUCCESS -> QuizStubs.SUCCESS
    RoundRequestDebugStubs.NOT_FOUND -> QuizStubs.NOT_FOUND
    RoundRequestDebugStubs.BAD_ID -> QuizStubs.BAD_ID
    RoundRequestDebugStubs.BAD_GAME_ID -> QuizStubs.BAD_GAME_ID
    else -> QuizStubs.NONE
}

private fun QuestionRequestDebugStubs?.transportToStubCase(): QuizStubs = when (this) {
    QuestionRequestDebugStubs.SUCCESS -> QuizStubs.SUCCESS
    QuestionRequestDebugStubs.NOT_FOUND -> QuizStubs.NOT_FOUND
    QuestionRequestDebugStubs.BAD_ID -> QuizStubs.BAD_ID
    QuestionRequestDebugStubs.BAD_FORMULATION -> QuizStubs.BAD_FORMULATION
    QuestionRequestDebugStubs.BAD_ANSWERS -> QuizStubs.BAD_ANSWERS
    QuestionRequestDebugStubs.BAD_TERMS -> QuizStubs.BAD_TERMS
    else -> QuizStubs.NONE
}

fun QuizContext.fromTransport(request: GameCreateRequest) {
    command = QuizCommand.CREATE_GAME
    requestId = request.requestId()
    gameRequest = request.game?.toInternal() ?: QuizGame()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: RoundCreateRequest) {
    command = QuizCommand.CREATE_ROUND
    requestId = request.requestId()
    roundRequest = request.round?.toInternal() ?: QuizRound()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: QuestionCreateRequest) {
    command = QuizCommand.CREATE_QUESTION
    requestId = request.requestId()
    questionRequest = request.question?.toInternal() ?: QuizQuestion()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: GameReadRequest) {
    command = QuizCommand.READ_GAME
    requestId = request.requestId()
    gameRequest = request.game?.gameId.toGameWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: RoundReadRequest) {
    command = QuizCommand.READ_ROUND
    requestId = request.requestId()
    roundRequest = request.round?.roundId.toRoundWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: QuestionReadRequest) {
    command = QuizCommand.READ_QUESTION
    requestId = request.requestId()
    questionRequest = request.question?.questionId.toQuestionWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: GameUpdateRequest) {
    command = QuizCommand.UPDATE_GAME
    requestId = request.requestId()
    gameRequest = request.game?.toInternal() ?: QuizGame()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: RoundUpdateRequest) {
    command = QuizCommand.UPDATE_ROUND
    requestId = request.requestId()
    roundRequest = request.round?.toInternal() ?: QuizRound()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: QuestionUpdateRequest) {
    command = QuizCommand.UPDATE_QUESTION
    requestId = request.requestId()
    questionRequest = request.question?.toInternal() ?: QuizQuestion()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: GameDeleteRequest) {
    command = QuizCommand.DELETE_GAME
    requestId = request.requestId()
    gameRequest = request.game?.gameId.toGameWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: RoundDeleteRequest) {
    command = QuizCommand.DELETE_ROUND
    requestId = request.requestId()
    roundRequest = request.round?.roundId.toRoundWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: QuestionDeleteRequest) {
    command = QuizCommand.DELETE_QUESTION
    requestId = request.requestId()
    questionRequest = request.question?.questionId.toQuestionWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: GameReadAllRequest) {
    command = QuizCommand.READ_ALL_GAMES
    requestId = request.requestId()
    gamesSelectorRequest = QuizGamesSelector()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: RoundReadAllRequest) {
    command = QuizCommand.READ_ALL_ROUNDS
    requestId = request.requestId()
    roundsSelectorRequest = request.selector?.toInternal() ?: QuizRoundsSelector()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizContext.fromTransport(request: QuestionReadAllRequest) {
    command = QuizCommand.READ_ALL_QUESTIONS
    requestId = request.requestId()
    questionsSelectorRequest = request.selector?.toInternal() ?: QuizQuestionsSelector()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

private fun GameCreateObject.toInternal() = QuizGame(
    title = title ?: "",
    description = description ?: ""
)

private fun GameUpdateObject.toInternal() = QuizGame(
    id = id.toGameId(),
    title = title ?: "",
    description = description ?: ""
)

private fun RoundCreateObject.toInternal() = QuizRound(
    gameId = gameId.toGameId(),
    title = title ?: "",
    description = description ?: ""
)

private fun RoundUpdateObject.toInternal() = QuizRound(
    id = id.toRoundId(),
    gameId = gameId.toGameId(),
    title = title ?: "",
    description = description ?: ""
)

private fun QuestionCreateObject.toInternal() = QuizQuestion(
    gameId = gameId.toGameId(),
    roundId = roundId.toRoundId(),
    type = questionType.toInternal(),
    formulation = formulation ?: "",
    answers = answers?.map { it.toInternal() }?.toMutableList() ?: mutableListOf(),
    matchingTerms = matchingTerms?.map { it.toInternal() }?.toMutableList() ?: mutableListOf()
)

private fun QuestionUpdateObject.toInternal() = QuizQuestion(
    id = id.toQuestionId(),
    gameId = gameId.toGameId(),
    roundId = roundId.toRoundId(),
    type = questionType.toInternal(),
    formulation = formulation ?: "",
    answers = answers?.map { it.toInternal() }?.toMutableList() ?: mutableListOf(),
    matchingTerms = matchingTerms?.map { it.toInternal() }?.toMutableList() ?: mutableListOf()
)

private fun RoundsSelector.toInternal() = QuizRoundsSelector(
    gameId = gameId.toGameId()
)

private fun QuestionsSelector.toInternal() = QuizQuestionsSelector(
    gameId = gameId.toGameId(),
    roundId = roundId.toRoundId()
)

private fun QuestionType?.toInternal() = when(this) {
    QuestionType.OPEN_QUESTION -> QuizQuestionType.OPEN_QUESTION
    QuestionType.MULTIPLE_OPTIONS_QUESTION -> QuizQuestionType.MULTIPLE_OPTIONS_QUESTION
    QuestionType.MATCHING_TERMS_QUESTION -> QuizQuestionType.MATCHING_TERMS_QUESTION
    else -> QuizQuestionType.NONE
}

private fun QuestionAnswer.toInternal() = QuizQuestionAnswer(
    formulation = formulation ?: "",
    isRight = isRight ?: false
)

private fun QuestionMatchingTerm.toInternal() = QuizQuestionMatchingTerm(
    term = term ?: "",
    definition = definition ?: ""
)