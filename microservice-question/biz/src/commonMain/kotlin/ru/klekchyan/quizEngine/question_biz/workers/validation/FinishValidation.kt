package ru.klekchyan.quizEngine.question_biz.workers.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState

fun CorChainDsl<QuizQuestionContext>.finishQuestionValidation(title: String) = worker {
    this.title = title
    on { state == QuizQuestionState.RUNNING }
    handle {
        questionValidated = questionValidating
    }
}

fun CorChainDsl<QuizQuestionContext>.finishQuestionsSelectorValidation(title: String) = worker {
    this.title = title
    on { state == QuizQuestionState.RUNNING }
    handle {
        questionsSelectorValidated = questionsSelectorValidating
    }
}