package ru.klekchyan.quizEngine.question_biz.validation

import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand
import kotlin.test.Test

class BizValidationUpdateTest {
    private val command = QuizQuestionCommand.UPDATE
    private val processor = QuizQuestionProcessor()

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdBadFormat(command, processor)

    @Test fun correctGameId() = validationGameIdCorrect(command, processor)
    @Test fun emptyGameId() = validationGameIdEmpty(command, processor)
    @Test fun badFormatGameId() = validationGameIdBadFormat(command, processor)

    @Test fun correctRoundId() = validationRoundIdCorrect(command, processor)
    @Test fun emptyRoundId() = validationRoundIdEmpty(command, processor)
    @Test fun badFormatRoundId() = validationRoundIdBadFormat(command, processor)

    @Test fun correctFormulation() = validationFormulationCorrect(command, processor)
    @Test fun trimFormulation() = validationFormulationTrim(command, processor)
    @Test fun emptyFormulation() = validationFormulationEmpty(command, processor)

    @Test fun correctType() = validationTypeCorrect(command, processor)
    @Test fun emptyType() = validationTypeEmpty(command, processor)

    @Test fun correctOpenQuestion() = validationOpenQuestionCorrect(command, processor)
    @Test fun openQuestionWithAnswers() = validationOpenQuestionWithAnswers(command, processor)
    @Test fun openQuestionWithMatchingTerms() = validationOpenQuestionWithTerms(command, processor)

    @Test fun correctMultipleOptionsQuestion() = validationMultipleOptionsQuestionCorrect(command, processor)
    @Test fun multipleOptionsQuestionWithTerms() = validationMultipleOptionsQuestionWithTerms(command, processor)
    @Test fun multipleOptionsQuestionWithoutAnswers() = validationMultipleOptionsQuestionWithoutAnswers(command, processor)
    @Test fun multipleOptionsQuestionWithoutRightAnswer() = validationMultipleOptionsQuestionWithoutRightAnswer(command, processor)
    @Test fun multipleOptionsQuestionWithSeveralRightAnswers() = validationMultipleOptionsQuestionWithSeveralRightAnswers(command, processor)
    @Test fun multipleOptionsQuestionAnswersFormulation() = validationMultipleOptionsQuestionAnswersFormulation(command, processor)

    @Test fun correctMatchingTermsQuestion() = validationMatchingTermsQuestionCorrect(command, processor)
    @Test fun matchingTermsQuestionWithoutTerms() = validationMatchingTermsQuestionWithoutTerms(command, processor)
    @Test fun matchingTermsQuestionWithAnswers() = validationMatchingTermsQuestionWithAnswers(command, processor)
    @Test fun matchingTermsQuestionEmptyTerm() = validationMatchingTermsQuestionEmptyTerm(command, processor)
    @Test fun matchingTermsQuestionEmptyDefinition() = validationMatchingTermsQuestionEmptyDefinition(command, processor)
}