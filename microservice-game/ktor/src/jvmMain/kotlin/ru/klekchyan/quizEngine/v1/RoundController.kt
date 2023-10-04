package ru.klekchyan.quizEngine.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.klekchyan.quizEngine.QuizGameContext
import ru.klekchyan.quizEngine.QuizGameProcessor
import ru.klekchyan.quizEngine.api.v1.models.*

suspend fun ApplicationCall.create(processor: QuizGameProcessor) {
    val request = receive<GameCreateRequest>()
    val context = QuizGameContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportCreateGame())
}

suspend fun ApplicationCall.read(processor: QuizGameProcessor) {
    val request = receive<GameReadRequest>()
    val context = QuizGameContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportReadGame())
}

suspend fun ApplicationCall.update(processor: QuizGameProcessor) {
    val request = receive<GameUpdateRequest>()
    val context = QuizGameContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportUpdateGame())
}

suspend fun ApplicationCall.delete(processor: QuizGameProcessor) {
    val request = receive<GameDeleteRequest>()
    val context = QuizGameContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportDeleteGame())
}

suspend fun ApplicationCall.readAll(processor: QuizGameProcessor) {
    val request = receive<GameReadAllRequest>()
    val context = QuizGameContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportReadAllGames())
}