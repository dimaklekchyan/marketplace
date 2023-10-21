package ru.klekchyan.quizEngine.question_ktor.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.question_mappers.*

suspend fun ApplicationCall.create(processor: QuizQuestionProcessor) {
    val request = receive<QuestionCreateRequest>()
    val context = QuizQuestionContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportCreateQuestion())
}

suspend fun ApplicationCall.read(processor: QuizQuestionProcessor) {
    val request = receive<QuestionReadRequest>()
    val context = QuizQuestionContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportReadQuestion())
}

suspend fun ApplicationCall.update(processor: QuizQuestionProcessor) {
    val request = receive<QuestionUpdateRequest>()
    val context = QuizQuestionContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportUpdateQuestion())
}

suspend fun ApplicationCall.delete(processor: QuizQuestionProcessor) {
    val request = receive<QuestionDeleteRequest>()
    val context = QuizQuestionContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportDeleteQuestion())
}

suspend fun ApplicationCall.readAll(processor: QuizQuestionProcessor) {
    val request = receive<QuestionReadAllRequest>()
    val context = QuizQuestionContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportReadAllQuestions())
}