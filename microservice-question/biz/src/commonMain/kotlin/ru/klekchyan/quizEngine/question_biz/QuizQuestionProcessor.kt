package ru.klekchyan.quizEngine.question_biz

import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.rootChain
import models.QuizCommonEntityId
import ru.klekchyan.quizEngine.question_biz.groups.operation
import ru.klekchyan.quizEngine.question_biz.groups.stubs
import ru.klekchyan.quizEngine.question_biz.groups.validation
import ru.klekchyan.quizEngine.question_biz.workers.*
import ru.klekchyan.quizEngine.question_biz.workers.stubs.*
import ru.klekchyan.quizEngine.question_biz.workers.stubs.stubCreateSuccess
import ru.klekchyan.quizEngine.question_biz.workers.stubs.stubDBError
import ru.klekchyan.quizEngine.question_biz.workers.stubs.stubValidationBadId
import ru.klekchyan.quizEngine.question_biz.workers.stubs.stubValidationBadRoundId
import ru.klekchyan.quizEngine.question_biz.workers.validation.*
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.QuizQuestionCorSettings
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand

class QuizQuestionProcessor(
    @Suppress("unused")
    private val corSettings: QuizQuestionCorSettings = QuizQuestionCorSettings.NONE
) {
    suspend fun exec(ctx: QuizQuestionContext) {
        BusinessChain.exec(ctx.apply { settings = corSettings })
    }

    companion object {
        val BusinessChain = rootChain<QuizQuestionContext> {
            initChain("Инициализация бизнес-цепочки")
            operation("Create", QuizQuestionCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadGameId("Имитация ошибки валидации идентификатора игры")
                    stubValidationBadRoundId("Имитация ошибки валидации идентификатора раунда")
                    stubValidationBadType("Имитация ошибки валидации типа")
                    stubValidationBadFormulation("Имитация ошибки валидации формулировки")
                    stubValidationBadAnswers("Имитация ошибки валидации ответов")
                    stubValidationBadMatchingTerms("Имитация ошибки валидации терминов для совпадений")
                    stubDBError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: стаб недопустим")
                }
                validation {
                    worker("Подготовка к валидации") { questionValidating = questionRequest.deepCopy() }
                    worker("Обнуляем ID") { questionValidating.id = QuizCommonEntityId.NONE }
                    worker("Чистим формулировку") { questionValidating.formulation = questionValidating.formulation.trim() }

                    validateGameIdNotEmpty("Проверка, что gameId не пустой")
                    validateGameIdProperFormat("Проверка, что gameId верного формата")
                    validateRoundIdNotEmpty("Проверка, что roundId не пустой")
                    validateRoundIdProperFormat("Проверка, что roundId верного формата")
                    validateFormulationNotEmpty("Проверка, что формулировка не пуста")
                    validateTypeNotEmpty("Проверка, что тип не пустой")
                    validateOpenQuestionHasNotAnswers("Проверка, что открытый тип вопроса не должен содержать вариантов ответа")
                    validateOpenQuestionHasNotMatchingTerms("Проверка, что открытый тип вопроса не должен содержать терминов для соответствия")
                    validateMultipleOptionsQuestionHasNotMatchingTerms("Проверка, что вопрос с вариантами ответов не должен содержать термины для сопоставления")
                    validateMultipleOptionsQuestionHasRightAnswer("Проверка, что вопрос с вариантами ответов имеет один правильный вариант")
                    validateAnswersFormulationNotEmpty("Проверка, что формулировки ответов на вопросы не пустые")
                    validateMatchingTermsQuestionHasTerms("Проверка, что вопрос с терминами для сопоставления имеет термины")
                    validateMatchingTermsQuestionHasNotAnswers("Проверка, что вопрос с терминами для сопоставления не должен содержать вариантов ответа")
                    validateMatchingTermsFormulationNotEmpty("Проверка, что формулировки терминов для сопоставления не пусты")
                    validateMatchingTermsDefinitionNotEmpty("Проверка, что определения терминов для сопоставления не пусты")

                    finishQuestionValidation("Успешное завершение процесса валидации")
                }
            }
            operation("Create", QuizQuestionCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDBError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: стаб недопустим")
                }
                validation {
                    worker("Подготовка к валидации") { questionValidating = questionRequest.deepCopy() }
                    worker("Очистка id") { questionValidating.id = QuizCommonEntityId(questionValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка, что id не пустой")
                    validateIdProperFormat("Проверка, что id верного формата")

                    finishQuestionValidation("Успешное завершение процесса валидации")
                }
            }
            operation("Create", QuizQuestionCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubValidationBadGameId("Имитация ошибки валидации идентификатора игры")
                    stubValidationBadRoundId("Имитация ошибки валидации идентификатора раунда")
                    stubValidationBadType("Имитация ошибки валидации типа")
                    stubValidationBadFormulation("Имитация ошибки валидации формулировки")
                    stubValidationBadAnswers("Имитация ошибки валидации ответов")
                    stubValidationBadMatchingTerms("Имитация ошибки валидации терминов для совпадений")
                    stubDBError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: стаб недопустим")
                }
                validation {
                    worker("Подготовка к валидации") { questionValidating = questionRequest.deepCopy() }
                    worker("Очистка id") { questionValidating.id = QuizCommonEntityId(questionValidating.id.asString().trim()) }
                    worker("Чистим формулировку") { questionValidating.formulation = questionValidating.formulation.trim() }

                    validateIdNotEmpty("Проверка, что id не пустой")
                    validateIdProperFormat("Проверка, что id верного формата")
                    validateGameIdNotEmpty("Проверка, что gameId не пустой")
                    validateGameIdProperFormat("Проверка, что gameId верного формата")
                    validateRoundIdNotEmpty("Проверка, что roundId не пустой")
                    validateRoundIdProperFormat("Проверка, что roundId верного формата")
                    validateFormulationNotEmpty("Проверка, что формулировка не пуста")
                    validateTypeNotEmpty("Проверка, что тип не пустой")
                    validateOpenQuestionHasNotAnswers("Проверка, что открытый тип вопроса не должен содержать вариантов ответа")
                    validateOpenQuestionHasNotMatchingTerms("Проверка, что открытый тип вопроса не должен содержать терминов для соответствия")
                    validateMultipleOptionsQuestionHasNotMatchingTerms("Проверка, что вопрос с вариантами ответов не должен содержать термины для сопоставления")
                    validateMultipleOptionsQuestionHasRightAnswer("Проверка, что вопрос с вариантами ответов имеет один правильный вариант")
                    validateAnswersFormulationNotEmpty("Проверка, что формулировки ответов на вопросы не пустые")
                    validateMatchingTermsQuestionHasTerms("Проверка, что вопрос с терминами для сопоставления имеет термины")
                    validateMatchingTermsQuestionHasNotAnswers("Проверка, что вопрос с терминами для сопоставления не должен содержать вариантов ответа")
                    validateMatchingTermsFormulationNotEmpty("Проверка, что формулировки терминов для сопоставления не пусты")
                    validateMatchingTermsDefinitionNotEmpty("Проверка, что определения терминов для сопоставления не пусты")

                    finishQuestionValidation("Успешное завершение процесса валидации")
                }
            }
            operation("Create", QuizQuestionCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubCannotDelete("Имитация ошибки невозможности удаления")
                    stubDBError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: стаб недопустим")
                }
                validation {
                    worker("Подготовка к валидации") { questionValidating = questionRequest.deepCopy() }
                    worker("Очистка id") { questionValidating.id = QuizCommonEntityId(questionValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка, что id не пустой")
                    validateIdProperFormat("Проверка, что id верного формата")

                    finishQuestionValidation("Успешное завершение процесса валидации")
                }
            }
            operation("Create", QuizQuestionCommand.READ_ALL) {
                stubs("Обработка стабов") {
                    stubReadAllSuccess("Имитация успешной обработки")
                    stubValidationBadGameId("Имитация ошибки валидации идентификатора игры")
                    stubValidationBadRoundId("Имитация ошибки валидации идентификатора раунда")
                    stubDBError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: стаб недопустим")
                }
                validation {
                    worker("Подготовка к валидации") { questionsSelectorValidating = questionsSelectorRequest.copy() }
                    validateSelectorGameIdNotEmpty("Проверка, что gameId не пустой")
                    validateSelectorGameIdProperFormat("Проверка, что gameId верного формата")
                    validateSelectorRoundIdNotEmpty("Проверка, что roundId не пустой")
                    validateSelectorRoundIdProperFormat("Проверка, что roundId верного формата")

                    finishQuestionsSelectorValidation("Успешное завершение процесса валидации")
                }
            }
        }.build()
    }
}


