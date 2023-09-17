package ru.klekchyan.quizEngine.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.klekchyan.quizEngine.QuizProcessor

fun Route.v1Question(processor: QuizProcessor) {
    route("question") {
        post("create") {
            call.createQuestion(processor)
        }
    }
}