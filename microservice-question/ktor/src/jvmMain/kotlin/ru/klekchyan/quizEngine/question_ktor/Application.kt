package ru.klekchyan.quizEngine.question_ktor

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.webSocket
import org.slf4j.event.Level
import ru.klekchyan.quizEngine.questions_api.questionApiV1Mapper
import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_ktor.v1.v1Question
import ru.klekchyan.quizEngine.question_ktor.v1.wsHandlerV1

// function with config (application.yaml)
fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.yaml
fun Application.module(processor: QuizQuestionProcessor = QuizQuestionProcessor()) {
    install(io.ktor.server.websocket.WebSockets)
    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        anyHost() // TODO remove
    }

    install(CallLogging) {
        level = Level.INFO
    }

    @Suppress("OPT_IN_USAGE")
    install(Locations)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("questions") {
            route("v1") {
                install(ContentNegotiation) {
                    json(questionApiV1Mapper)
                }
                v1Question(processor)
                webSocket("/ws") {
                    wsHandlerV1(processor)
                }
            }
        }
    }
}
