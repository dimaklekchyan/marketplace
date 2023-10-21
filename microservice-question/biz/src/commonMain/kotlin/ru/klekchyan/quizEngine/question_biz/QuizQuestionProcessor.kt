package ru.klekchyan.quizEngine.question_biz

import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_stubs.QuizQuestionStub
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionWorkMode

class QuizQuestionProcessor {
    suspend fun exec(ctx: QuizQuestionContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == QuizQuestionWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            QuizQuestionCommand.READ_ALL -> {
                val questions = QuizQuestionStub.prepareQuestionsList(
                    gameId = ctx.questionsSelectorRequest.gameId.asString(),
                    roundId = ctx.questionsSelectorRequest.roundId.asString()
                )
                ctx.questionsResponse.addAll(questions)
            }
            else -> {
                ctx.questionResponse = QuizQuestionStub.get()
            }
        }
    }
}
