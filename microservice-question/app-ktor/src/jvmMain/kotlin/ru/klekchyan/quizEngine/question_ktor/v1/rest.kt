package ru.klekchyan.quizEngine.question_ktor.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.klekchyan.quizEngine.question_ktor.QuizQuestionAppSettings

fun Route.v1Question(appSettings: QuizQuestionAppSettings) {
    post("create") {
        call.create(appSettings)
    }
    post("read") {
        call.read(appSettings)
    }
    post("update") {
        call.update(appSettings)
    }
    post("delete") {
        call.delete(appSettings)
    }
    post("readAll") {
        call.readAll(appSettings)
    }
}