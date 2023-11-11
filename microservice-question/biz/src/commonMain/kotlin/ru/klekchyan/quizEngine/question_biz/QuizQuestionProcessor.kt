package ru.klekchyan.quizEngine.question_biz

import com.crowdproj.kotlin.cor.rootChain
import ru.klekchyan.quizEngine.question_biz.groups.operation
import ru.klekchyan.quizEngine.question_biz.groups.stubs
import ru.klekchyan.quizEngine.question_biz.workers.*
import ru.klekchyan.quizEngine.question_biz.workers.stubs.*
import ru.klekchyan.quizEngine.question_biz.workers.stubs.stubCreateSuccess
import ru.klekchyan.quizEngine.question_biz.workers.stubs.stubDBError
import ru.klekchyan.quizEngine.question_biz.workers.stubs.stubValidationBadId
import ru.klekchyan.quizEngine.question_biz.workers.stubs.stubValidationBadRoundId
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
            }
            operation("Create", QuizQuestionCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubDBError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: стаб недопустим")
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
            }
            operation("Create", QuizQuestionCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации идентификатора")
                    stubCannotDelete("Имитация ошибки невозможности удаления")
                    stubDBError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: стаб недопустим")
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
            }
        }.build()
    }
}


