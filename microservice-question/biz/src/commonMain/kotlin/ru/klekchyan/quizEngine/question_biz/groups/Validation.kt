package ru.klekchyan.quizEngine.question_biz.groups

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState

internal fun CorChainDsl<QuizQuestionContext>.validation(block: CorChainDsl<QuizQuestionContext>.() -> Unit) = chain {
    this.title = "Валидация"
    on { state == QuizQuestionState.RUNNING }
    block()
}