package ru.klekchyan.quizEngine.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.klekchyan.quizEngine.QuizRoundProcessor

fun Route.v1Round(processor: QuizRoundProcessor) {
    post("v1/create") {
        call.create(processor)
    }
    post("v1/read") {
        call.read(processor)
    }
    post("v1/update") {
        call.update(processor)
    }
    post("v1/delete") {
        call.delete(processor)
    }
    post("v1/readAll") {
        call.readAll(processor)
    }
}