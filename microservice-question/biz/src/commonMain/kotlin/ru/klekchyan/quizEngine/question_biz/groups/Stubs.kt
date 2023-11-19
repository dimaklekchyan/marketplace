package ru.klekchyan.quizEngine.question_biz.groups

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionWorkMode
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs

internal fun CorChainDsl<QuizQuestionContext>.stubs(
    title: String,
    block: CorChainDsl<QuizQuestionContext>.() -> Unit
) = chain {
    this.title = title
    on { workMode == QuizQuestionWorkMode.STUB && this.stubCase != QuizQuestionStubs.NONE }
    block()
}