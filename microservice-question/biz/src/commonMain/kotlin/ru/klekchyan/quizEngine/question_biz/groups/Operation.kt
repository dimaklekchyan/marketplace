package ru.klekchyan.quizEngine.question_biz.groups

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand

internal fun CorChainDsl<QuizQuestionContext>.operation(
    title: String,
    command: QuizQuestionCommand,
    block: CorChainDsl<QuizQuestionContext>.() -> Unit
) = chain {
    this.title = title
    on { this.command == command }
    block()
}