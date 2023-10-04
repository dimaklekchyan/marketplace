package ru.klekchyan.quizEngine.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.klekchyan.quizEngine.QuizRoundContext
import ru.klekchyan.quizEngine.QuizRoundProcessor
import ru.klekchyan.quizEngine.api.v1.models.*

suspend fun ApplicationCall.create(processor: QuizRoundProcessor) {
    val request = receive<RoundCreateRequest>()
    val context = QuizRoundContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportCreateRound())
}

suspend fun ApplicationCall.read(processor: QuizRoundProcessor) {
    val request = receive<RoundReadRequest>()
    val context = QuizRoundContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportReadRound())
}

suspend fun ApplicationCall.update(processor: QuizRoundProcessor) {
    val request = receive<RoundUpdateRequest>()
    val context = QuizRoundContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportUpdateRound())
}

suspend fun ApplicationCall.delete(processor: QuizRoundProcessor) {
    val request = receive<RoundDeleteRequest>()
    val context = QuizRoundContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportDeleteRound())
}

suspend fun ApplicationCall.readAll(processor: QuizRoundProcessor) {
    val request = receive<RoundReadAllRequest>()
    val context = QuizRoundContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportReadAllRounds())
}