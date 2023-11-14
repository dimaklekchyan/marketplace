package ru.klekchyan.quizEngine.question_biz.workers.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.helpers.errorValidation
import ru.klekchyan.quizEngine.question_common.helpers.fail
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionType

fun CorChainDsl<QuizQuestionContext>.validateMatchingTermsQuestionHasNotAnswers(title: String) = worker {
    this.title = title
    on {
        questionValidating.type == QuizQuestionType.MATCHING_TERMS_QUESTION &&
        questionValidating.answers.isNotEmpty()
    }
    handle {
        fail(
            errorValidation(
                field = "answers",
                violationCode = "notEmpty",
                description = "matching terms question type must not contain answers"
            )
        )
    }
}