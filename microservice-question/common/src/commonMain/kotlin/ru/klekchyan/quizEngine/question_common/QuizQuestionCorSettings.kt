package ru.klekchyan.quizEngine.question_common

import ru.klekchyan.quizEngine.logging.common.LoggerProvider

data class QuizQuestionCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
) {
    companion object {
        val NONE = QuizQuestionCorSettings()
    }
}