package ru.klekchyan.quizEngine.question_biz.workers.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import models.QuizCommonEntityId
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.helpers.errorValidation
import ru.klekchyan.quizEngine.question_common.helpers.fail

fun CorChainDsl<QuizQuestionContext>.validateIdProperFormat(title: String) = worker {
    this.title = title
    on {
        questionValidating.id != QuizCommonEntityId.NONE &&
                !questionValidating.id.asString().matches(QuizCommonEntityId.formatRegex)
    }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "field doesn't match proper format"
            )
        )
    }
}