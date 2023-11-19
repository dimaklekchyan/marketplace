package ru.klekchyan.quizEngine.question_ktor

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.klekchyan.quizEngine.api.v1.models.IRequestQuestion
import ru.klekchyan.quizEngine.api.v1.models.IResponseQuestion
import ru.klekchyan.quizEngine.app_common.controllerHelper
import ru.klekchyan.quizEngine.question_mappers.fromTransport
import ru.klekchyan.quizEngine.question_mappers.toTransport
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequestQuestion, reified R : IResponseQuestion> ApplicationCall.processV1(
    appSettings: QuizQuestionAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    getRequest = {
        fromTransport(receive<Q>())
    },
    toResponse = {
        respond(toTransport())
    },
    clazz = clazz,
    logId = logId
)
