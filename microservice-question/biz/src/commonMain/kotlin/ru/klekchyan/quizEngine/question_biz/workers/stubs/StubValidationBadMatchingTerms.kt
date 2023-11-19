package ru.klekchyan.quizEngine.question_biz.workers.stubs

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import models.QuizError
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs

internal fun CorChainDsl<QuizQuestionContext>.stubValidationBadMatchingTerms(title: String) = worker {
    this.title = title
    on { stubCase == QuizQuestionStubs.BAD_TERMS && state == QuizQuestionState.RUNNING }
    handle {
        state = QuizQuestionState.FAILING
        this.errors.add(
            QuizError(
                group = "validation",
                code = "validation-matching-terms",
                field = "matchingTerms",
                message = "Wrong matchingTerms field"
            )
        )
    }
}