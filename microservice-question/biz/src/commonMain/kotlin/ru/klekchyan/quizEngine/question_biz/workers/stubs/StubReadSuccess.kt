package ru.klekchyan.quizEngine.question_biz.workers.stubs

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import models.QuizCommonEntityId
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState
import ru.klekchyan.quizEngine.question_common.stubs.QuizQuestionStubs
import ru.klekchyan.quizEngine.question_stubs.QuizQuestionStub

internal fun CorChainDsl<QuizQuestionContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == QuizQuestionStubs.SUCCESS && state == QuizQuestionState.RUNNING }
    handle {
        state = QuizQuestionState.FINISHING
        val stub = QuizQuestionStub.prepareResult {
            questionRequest.id.takeIf { it != QuizCommonEntityId.NONE }?.also { this.id = it }
        }
        questionResponse = stub
    }
}