package ru.klekchyan.quizEngine.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.klekchyan.quizEngine.QuizGameProcessor

fun Route.v1Game(processor: QuizGameProcessor) {
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