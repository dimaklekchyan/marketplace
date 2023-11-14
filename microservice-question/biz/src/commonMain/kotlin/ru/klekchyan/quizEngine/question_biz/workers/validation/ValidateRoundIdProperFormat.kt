package ru.klekchyan.quizEngine.question_biz.workers.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import models.QuizCommonEntityId
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.helpers.errorValidation
import ru.klekchyan.quizEngine.question_common.helpers.fail

fun CorChainDsl<QuizQuestionContext>.validateRoundIdProperFormat(title: String) = worker {
    this.title = title
    on {
        questionValidating.roundId != QuizCommonEntityId.NONE &&
        !questionValidating.roundId.asString().matches(QuizCommonEntityId.formatRegex)
    }
    handle {
        fail(
            errorValidation(
                field = "roundId",
                violationCode = "badFormat",
                description = "field doesn't match proper format"
            )
        )
    }
}