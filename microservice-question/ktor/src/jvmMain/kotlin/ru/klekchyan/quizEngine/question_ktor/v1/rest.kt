package ru.klekchyan.quizEngine.question_ktor.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor

fun Route.v1Question(processor: QuizQuestionProcessor) {
    post("create") {
        call.create(processor)
    }
    post("read") {
        call.read(processor)
    }
    post("update") {
        call.update(processor)
    }
    post("delete") {
        call.delete(processor)
    }
    post("readAll") {
        call.readAll(processor)
    }
}