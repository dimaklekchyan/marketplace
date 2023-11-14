package ru.klekchyan.quizEngine.question_biz.validation

import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand
import kotlin.test.Test

class BizValidationReadAllTest {

    private val command = QuizQuestionCommand.READ_ALL
    private val processor = QuizQuestionProcessor()

    @Test fun correctSelector() = validationSelectorCorrect(command, processor)

    @Test fun selectorGameIdEmpty() = validationSelectorGameIdEmpty(command, processor)
    @Test fun selectorGameIdBadFormat() = validationSelectorGameIdBadFormat(command, processor)

    @Test fun selectorRoundIdEmpty() = validationSelectorRoundIdEmpty(command, processor)
    @Test fun selectorRoundIdBadFormat() = validationSelectorRoundIdBadFormat(command, processor)
}