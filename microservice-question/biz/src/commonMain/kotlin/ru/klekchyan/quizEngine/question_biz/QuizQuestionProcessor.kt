package ru.klekchyan.quizEngine.question_biz

import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.QuizQuestionCorSettings
import ru.klekchyan.quizEngine.question_stubs.QuizQuestionStub
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionState
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionWorkMode

class QuizQuestionProcessor(
    @Suppress("unused")
    private val corSettings: QuizQuestionCorSettings = QuizQuestionCorSettings.NONE
) {
    suspend fun exec(ctx: QuizQuestionContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == QuizQuestionWorkMode.STUB || ctx.command in arrayOf(QuizQuestionCommand.INIT, QuizQuestionCommand.FINISH)) {
            "Currently working only in STUB mode but mode is ${ctx.workMode}"
        }

        if (ctx.state == QuizQuestionState.NONE) ctx.state = QuizQuestionState.RUNNING

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
