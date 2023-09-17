package ru.klekchyan.quizEngine.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.klekchyan.quizEngine.QuizContext
import ru.klekchyan.quizEngine.QuizProcessor
import ru.klekchyan.quizEngine.api.v1.models.*

suspend fun ApplicationCall.createQuestion(processor: QuizProcessor) {
    val request = receive<QuestionCreateRequest>()
    val context = QuizContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportCreateQuestion())
}

suspend fun ApplicationCall.readQuestion(processor: QuizProcessor) {
    val request = receive<QuestionReadRequest>()
    val context = QuizContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportReadQuestion())
}

suspend fun ApplicationCall.updateQuestion(processor: QuizProcessor) {
    val request = receive<QuestionUpdateRequest>()
    val context = QuizContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportUpdateQuestion())
}

suspend fun ApplicationCall.deleteQuestion(processor: QuizProcessor) {
    val request = receive<QuestionDeleteRequest>()
    val context = QuizContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportDeleteQuestion())
}

suspend fun ApplicationCall.readAllQuestion(processor: QuizProcessor) {
    val request = receive<QuestionReadAllRequest>()
    val context = QuizContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportReadAllQuestions())
}