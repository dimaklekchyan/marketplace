package ru.klekchyan.quizEngine.quiestion_mappers_log

import kotlinx.datetime.Clock
import models.QuizCommonEntityId
import models.QuizRequestId
import ru.klekchyan.quizEngine.api.v1.models.QuestionLog
import ru.klekchyan.quizEngine.api.v1.models.QuestionLogModel
import ru.klekchyan.quizEngine.api.v1.models.QuestionSelectorLog
import ru.klekchyan.quizEngine.mappers_log.toLog
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.*

fun QuizQuestionContext.toLog(logId: String): QuestionLogModel {
    val questionNone = QuizQuestion()
    return QuestionLogModel(
        messageTime = Clock.System.now().toString(),
        logId = logId,
        source = "microservice-question",
        errors = errors.map { it.toLog() },
        requestId = requestId.takeIf { it != QuizRequestId.NONE }?.asString(),
        operation = command.toLog(),
        requestQuestion = questionRequest.takeIf { it != questionNone }?.toLog(),
        responseQuestion = questionResponse.takeIf { it != questionNone }?.toLog(),
        requestSelector = questionsSelectorRequest.takeIf { it != QuizQuestionsSelector() }?.toLog(),
        responseQuestions = questionsResponse.takeIf { it.isNotEmpty() }?.filter { it != questionNone }?.map { it.toLog() }
    )
}

private fun QuizQuestionCommand.toLog(): QuestionLogModel.Operation? = when(this) {
    QuizQuestionCommand.CREATE -> QuestionLogModel.Operation.CREATE
    QuizQuestionCommand.READ -> QuestionLogModel.Operation.READ
    QuizQuestionCommand.UPDATE -> QuestionLogModel.Operation.UPDATE
    QuizQuestionCommand.DELETE -> QuestionLogModel.Operation.DELETE
    QuizQuestionCommand.READ_ALL -> QuestionLogModel.Operation.READ_ALL
    QuizQuestionCommand.INIT -> QuestionLogModel.Operation.INIT
    QuizQuestionCommand.FINISH -> QuestionLogModel.Operation.FINISH
    QuizQuestionCommand.NONE -> null
}

private fun QuizQuestion.toLog() = QuestionLog(
    id = id.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
    gameId = gameId.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
    roundId = roundId.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
    questionType = type.toLog(),
    formulation = formulation.takeIf { it.isNotEmpty() },
    answers = answers.takeIf { it.isNotEmpty() }?.map { it.toLog() }?.toSet(),
    matchingTerms = matchingTerms.takeIf { it.isNotEmpty() }?.map { it.toLog() }?.toSet()
)

private fun QuizQuestionsSelector.toLog() = QuestionSelectorLog(
    gameId = gameId.takeIf { it != QuizCommonEntityId.NONE }?.asString(),
    roundId = roundId.takeIf { it != QuizCommonEntityId.NONE }?.asString()
)

private fun QuizQuestionType.toLog() =
    this.takeIf { it != QuizQuestionType.NONE }?.toString()

private fun QuizQuestionAnswer.toLog() = this.toString()

private fun QuizQuestionMatchingTerm.toLog() = this.toString()