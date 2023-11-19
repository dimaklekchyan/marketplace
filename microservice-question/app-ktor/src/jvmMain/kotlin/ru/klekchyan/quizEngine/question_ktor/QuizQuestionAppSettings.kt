package ru.klekchyan.quizEngine.question_ktor

import ru.klekchyan.quizEngine.app_common.IQuizQuestionAppSettings
import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_common.QuizQuestionCorSettings

data class QuizQuestionAppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: QuizQuestionCorSettings,
    override val processor: QuizQuestionProcessor = QuizQuestionProcessor(corSettings),
): IQuizQuestionAppSettings
