package ru.klekchyan.quizEngine.question_biz.workers

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState

internal fun CorChainDsl<QuizQuestionContext>.initChain(title: String) = worker {
    this.title = title
    on { state == QuizQuestionState.NONE }
    handle {
        state = QuizQuestionState.RUNNING
    }
}