package ru.klekchyan.quizEngine.question_biz.workers.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.helpers.errorValidation
import ru.klekchyan.quizEngine.question_common.helpers.fail

fun CorChainDsl<QuizQuestionContext>.validateSelectorRoundIdNotEmpty(title: String) = worker {
    this.title = title
    on { questionsSelectorValidating.roundId.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "roundId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}