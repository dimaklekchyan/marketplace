package ru.klekchyan.quizEngine.question_biz.workers.stubs

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import models.QuizCommonEntityId
import models.QuizError
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.helpers.fail
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs
import ru.klekchyan.quizEngine.question_stubs.QuizQuestionStub

internal fun CorChainDsl<QuizQuestionContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == QuizQuestionState.RUNNING }
    handle {
        fail(
            QuizError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}