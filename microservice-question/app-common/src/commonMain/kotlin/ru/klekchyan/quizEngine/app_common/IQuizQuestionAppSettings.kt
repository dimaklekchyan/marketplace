package ru.klekchyan.quizEngine.app_common

import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_common.QuizQuestionCorSettings

interface IQuizQuestionAppSettings {
    val processor: QuizQuestionProcessor
    val corSettings: QuizQuestionCorSettings
}
