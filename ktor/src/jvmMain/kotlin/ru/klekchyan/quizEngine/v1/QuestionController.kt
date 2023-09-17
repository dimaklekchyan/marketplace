package ru.klekchyan.quizEngine.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.klekchyan.quizEngine.QuizContext
import ru.klekchyan.quizEngine.QuizProcessor
import ru.klekchyan.quizEngine.api.v1.models.QuestionCreateRequest

suspend fun ApplicationCall.createQuestion(processor: QuizProcessor) {
    val request = receive<QuestionCreateRequest>()
    val context = QuizContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportCreateQuestion())
}