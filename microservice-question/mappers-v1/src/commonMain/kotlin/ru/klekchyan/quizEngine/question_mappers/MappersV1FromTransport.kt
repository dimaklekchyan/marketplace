package ru.klekchyan.quizEngine.question_mappers

import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.question_common.models.*
import ru.klekchyan.quizEngine.requestId
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs
import ru.klekchyan.quizEngine.toId
import ru.klekchyan.quizEngine.question_mappers.v1.exceptions.UnknownQuestionRequestClass

fun QuizQuestionContext.fromTransport(request: IRequestQuestion) = when (request) {
    is QuestionCreateRequest -> fromTransport(request)
    is QuestionReadRequest -> fromTransport(request)
    is QuestionUpdateRequest -> fromTransport(request)
    is QuestionDeleteRequest -> fromTransport(request)
    is QuestionReadAllRequest -> fromTransport(request)
    else -> throw UnknownQuestionRequestClass(request::class)
}

private fun String?.toQuestionWithId() = QuizQuestion(id = this.toId())

private fun QuestionRequestDebugMode?.transportToWorkMode(): QuizQuestionWorkMode = when (this) {
    QuestionRequestDebugMode.PROD -> QuizQuestionWorkMode.PROD
    QuestionRequestDebugMode.TEST -> QuizQuestionWorkMode.TEST
    QuestionRequestDebugMode.STUB -> QuizQuestionWorkMode.STUB
    else -> QuizQuestionWorkMode.PROD
}

private fun QuestionRequestDebugStubs?.transportToStubCase(): QuizQuestionStubs = when (this) {
    QuestionRequestDebugStubs.SUCCESS -> QuizQuestionStubs.SUCCESS
    QuestionRequestDebugStubs.NOT_FOUND -> QuizQuestionStubs.NOT_FOUND
    QuestionRequestDebugStubs.BAD_ID -> QuizQuestionStubs.BAD_ID
    QuestionRequestDebugStubs.BAD_ROUND_ID -> QuizQuestionStubs.BAD_ROUND_ID
    QuestionRequestDebugStubs.BAD_GAME_ID -> QuizQuestionStubs.BAD_GAME_ID
    QuestionRequestDebugStubs.BAD_FORMULATION -> QuizQuestionStubs.BAD_FORMULATION
    QuestionRequestDebugStubs.BAD_ANSWERS -> QuizQuestionStubs.BAD_ANSWERS
    QuestionRequestDebugStubs.BAD_TERMS -> QuizQuestionStubs.BAD_TERMS
    else -> QuizQuestionStubs.NONE
}

fun QuizQuestionContext.fromTransport(request: QuestionCreateRequest) {
    command = QuizQuestionCommand.CREATE
    requestId = request.requestId()
    questionRequest = request.question?.toInternal() ?: QuizQuestion()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizQuestionContext.fromTransport(request: QuestionReadRequest) {
    command = QuizQuestionCommand.READ
    requestId = request.requestId()
    questionRequest = request.question?.id.toQuestionWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizQuestionContext.fromTransport(request: QuestionUpdateRequest) {
    command = QuizQuestionCommand.UPDATE
    requestId = request.requestId()
    questionRequest = request.question?.toInternal() ?: QuizQuestion()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizQuestionContext.fromTransport(request: QuestionDeleteRequest) {
    command = QuizQuestionCommand.DELETE
    requestId = request.requestId()
    questionRequest = request.question?.id.toQuestionWithId()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

fun QuizQuestionContext.fromTransport(request: QuestionReadAllRequest) {
    command = QuizQuestionCommand.READ_ALL
    requestId = request.requestId()
    questionsSelectorRequest = request.selector?.toInternal() ?: QuizQuestionsSelector()
    workMode = request.debug?.mode.transportToWorkMode()
    stubCase = request.debug?.stub.transportToStubCase()
}

private fun QuestionCreateObject.toInternal() = QuizQuestion(
    gameId = gameId.toId(),
    roundId = roundId.toId(),
    type = questionType.toInternal(),
    formulation = formulation ?: "",
    answers = answers?.map { it.toInternal() }?.toMutableList() ?: mutableListOf(),
    matchingTerms = matchingTerms?.map { it.toInternal() }?.toMutableList() ?: mutableListOf()
)

private fun QuestionUpdateObject.toInternal() = QuizQuestion(
    id = id.toId(),
    gameId = gameId.toId(),
    roundId = roundId.toId(),
    type = questionType.toInternal(),
    formulation = formulation ?: "",
    answers = answers?.map { it.toInternal() }?.toMutableList() ?: mutableListOf(),
    matchingTerms = matchingTerms?.map { it.toInternal() }?.toMutableList() ?: mutableListOf()
)

private fun QuestionsSelector.toInternal() = QuizQuestionsSelector(
    gameId = gameId.toId(),
    roundId = roundId.toId()
)

private fun QuestionType?.toInternal() = when(this) {
    QuestionType.OPEN_QUESTION -> QuizQuestionType.OPEN_QUESTION
    QuestionType.MULTIPLE_OPTIONS_QUESTION -> QuizQuestionType.MULTIPLE_OPTIONS_QUESTION
    QuestionType.MATCHING_TERMS_QUESTION -> QuizQuestionType.MATCHING_TERMS_QUESTION
    else -> QuizQuestionType.NONE
}

private fun QuestionAnswer.toInternal() = QuizQuestionAnswer(
    formulation = formulation,
    isRight = isRight
)

private fun QuestionMatchingTerm.toInternal() = QuizQuestionMatchingTerm(
    term = term,
    definition = definition
)