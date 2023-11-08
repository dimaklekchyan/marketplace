package ru.klekchyan.quizEngine.app_common

import helpers.asQuizError
import kotlinx.datetime.Clock
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState
import ru.klekchyan.quizEngine.quiestion_mappers_log.toLog
//import ru.klekchyan.quizEngine.quiestion_mappers_log.toLog
import kotlin.reflect.KClass

suspend inline fun <T> IQuizQuestionAppSettings.controllerHelper(
    crossinline getRequest: suspend QuizQuestionContext.() -> Unit,
    crossinline toResponse: suspend QuizQuestionContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = QuizQuestionContext(
        timeStart = Clock.System.now(),
    )
    return try {
        logger.doWithLogging(logId) {
            ctx.getRequest()
            processor.exec(ctx)
            logger.info(
                msg = "Request $logId processed for ${clazz.simpleName}",
                marker = "BIZ",
                data = ctx.toLog(logId)
            )
            ctx.toResponse()
        }
    } catch (e: Throwable) {
        logger.doWithLogging("$logId-failure") {
            ctx.state = QuizQuestionState.FAILING
            ctx.errors.add(e.asQuizError())
            processor.exec(ctx)
            ctx.toResponse()
        }
        throw e
    }
}
