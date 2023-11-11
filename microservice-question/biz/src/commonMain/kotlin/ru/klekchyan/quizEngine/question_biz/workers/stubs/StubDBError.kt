package ru.klekchyan.quizEngine.question_biz.workers.stubs

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import models.QuizError
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs

internal fun CorChainDsl<QuizQuestionContext>.stubDBError(title: String) = worker {
    this.title = title
    on { stubCase == QuizQuestionStubs.DB_ERROR && state == QuizQuestionState.RUNNING }
    handle {
        state = QuizQuestionState.FAILING
        this.errors.add(
            QuizError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}