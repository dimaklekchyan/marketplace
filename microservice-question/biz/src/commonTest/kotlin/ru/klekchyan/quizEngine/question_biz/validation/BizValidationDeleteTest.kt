package ru.klekchyan.quizEngine.question_biz.validation

import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand
import kotlin.test.Test

class BizValidationDeleteTest {

    private val command = QuizQuestionCommand.DELETE
    private val processor = QuizQuestionProcessor()

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdBadFormat(command, processor)
}