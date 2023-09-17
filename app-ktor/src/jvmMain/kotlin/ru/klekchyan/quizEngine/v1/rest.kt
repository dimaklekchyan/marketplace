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
    route("question") {
        post("read") {
            call.readQuestion(processor)
        }
    }
    route("question") {
        post("update") {
            call.updateQuestion(processor)
        }
    }
    route("question") {
        post("delete") {
            call.deleteQuestion(processor)
        }
    }
    route("question") {
        post("readAll") {
            call.readAllQuestion(processor)
        }
    }
}