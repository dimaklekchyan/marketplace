package ru.klekchyan.quizEngine.question_ktor.plugins

import io.ktor.server.application.*
import ru.klekchyan.quizEngine.logging.common.LoggerProvider
import ru.klekchyan.quizEngine.logging.logback.quizLoggerLogback
import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_common.QuizQuestionCorSettings
import ru.klekchyan.quizEngine.question_ktor.QuizQuestionAppSettings

fun Application.initAppSettings(): QuizQuestionAppSettings {
    val corSettings = QuizQuestionCorSettings(
        loggerProvider = LoggerProvider { quizLoggerLogback("question-ktor") }
    )
    return QuizQuestionAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = QuizQuestionProcessor(corSettings),
    )
}
