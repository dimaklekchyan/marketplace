package ru.klekchyan.quizEngine.question_biz.workers.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.helpers.errorValidation
import ru.klekchyan.quizEngine.question_common.helpers.fail
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionType

fun CorChainDsl<QuizQuestionContext>.validateTypeNotEmpty(title: String) = worker {
    this.title = title
    on { questionValidating.type == QuizQuestionType.NONE }
    handle {
        fail(
            errorValidation(
                field = "type",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}