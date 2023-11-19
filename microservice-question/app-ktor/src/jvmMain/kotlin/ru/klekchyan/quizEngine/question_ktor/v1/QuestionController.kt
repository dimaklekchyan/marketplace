package ru.klekchyan.quizEngine.question_ktor.v1

import io.ktor.server.application.*
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.question_ktor.QuizQuestionAppSettings
import ru.klekchyan.quizEngine.question_ktor.processV1
import kotlin.reflect.KClass

private val clCreate: KClass<*> = ApplicationCall::create::class
suspend fun ApplicationCall.create(appSettings: QuizQuestionAppSettings) =
    processV1<QuestionCreateRequest, QuestionCreateResponse>(appSettings, clCreate, "createQuestion")

private val clRead: KClass<*> = ApplicationCall::create::class
suspend fun ApplicationCall.read(appSettings: QuizQuestionAppSettings) =
    processV1<QuestionReadRequest, QuestionReadResponse>(appSettings, clRead, "readQuestion")

private val clUpdate: KClass<*> = ApplicationCall::create::class
suspend fun ApplicationCall.update(appSettings: QuizQuestionAppSettings) =
    processV1<QuestionUpdateRequest, QuestionUpdateResponse>(appSettings, clUpdate, "updateQuestion")

private val clDelete: KClass<*> = ApplicationCall::create::class
suspend fun ApplicationCall.delete(appSettings: QuizQuestionAppSettings) =
    processV1<QuestionDeleteRequest, QuestionDeleteResponse>(appSettings, clDelete, "delereQuestion")

private val clReadAll: KClass<*> = ApplicationCall::create::class
suspend fun ApplicationCall.readAll(appSettings: QuizQuestionAppSettings) =
    processV1<QuestionReadAllRequest, QuestionReadResponse>(appSettings, clReadAll, "readAllQuestions")