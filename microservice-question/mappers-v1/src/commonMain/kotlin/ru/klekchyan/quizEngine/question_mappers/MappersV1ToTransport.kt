package ru.klekchyan.quizEngine.question_mappers

import models.QuizCommonEntityId
import models.QuizError
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.question_common.models.*
import ru.klekchyan.quizEngine.question_mappers.exceptions.UnknownQuizQuestionCommand

fun QuizQuestionContext.toTransport(): IResponseQuestion = when (val cmd = command) {
    QuizQuestionCommand.CREATE -> toTransportCreateQuestion()
    QuizQuestionCommand.READ -> toTransportReadQuestion()
    QuizQuestionCommand.UPDATE -> toTransportUpdateQuestion()
    QuizQuestionCommand.DELETE -> toTransportDeleteQuestion()
    QuizQuestionCommand.READ_ALL -> toTransportReadAllQuestions()
    QuizQuestionCommand.INIT -> toTransportInitQuestions()
    QuizQuestionCommand.FINISH -> throw UnknownQuizQuestionCommand(cmd)
    QuizQuestionCommand.NONE -> throw UnknownQuizQuestionCommand(cmd)
}

fun QuizQuestionContext.toTransportCreateQuestion() = QuestionCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.klekchyan.quizEngine.question_common.models.QuizQuestionState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    question = questionResponse.toTransport()
)

fun QuizQuestionContext.toTransportReadQuestion() = QuestionReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.klekchyan.quizEngine.question_common.models.QuizQuestionState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    question = questionResponse.toTransport()
)

fun QuizQuestionContext.toTransportUpdateQuestion() = QuestionUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.klekchyan.quizEngine.question_common.models.QuizQuestionState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    question = questionResponse.toTransport()
)

fun QuizQuestionContext.toTransportDeleteQuestion() = QuestionDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.klekchyan.quizEngine.question_common.models.QuizQuestionState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    question = questionResponse.toTransport()
)

fun QuizQuestionContext.toTransportReadAllQuestions() = QuestionReadAllResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.klekchyan.quizEngine.question_common.models.QuizQuestionState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport(),
    questions = questionsResponse.map { it.toTransport() }
)

fun QuizQuestionContext.toTransportInitQuestions(): QuestionInitResponse = QuestionInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == QuizQuestionState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransport()
)

private fun QuizQuestion.toTransport() = QuestionResponseObject(
    id = id.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
    gameId = gameId.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
    roundId = roundId.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
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