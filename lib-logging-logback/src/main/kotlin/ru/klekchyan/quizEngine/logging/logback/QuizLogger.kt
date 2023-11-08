package ru.klekchyan.quizEngine.logging.logback

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.klekchyan.quizEngine.logging.common.ILogWrapper
import kotlin.reflect.KClass

fun quizLoggerLogback(logger: Logger): ILogWrapper = QuizLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun quizLoggerLogback(clazz: KClass<*>): ILogWrapper = quizLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun quizLoggerLogback(loggerId: String): ILogWrapper = quizLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
