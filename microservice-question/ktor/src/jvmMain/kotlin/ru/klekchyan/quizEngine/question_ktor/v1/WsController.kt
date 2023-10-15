package ru.klekchyan.quizEngine.question_ktor.v1

import helpers.asQuizError
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.decodeFromString
import ru.klekchyan.quizEngine.api.v1.models.IRequestQuestion
import ru.klekchyan.quizEngine.question_biz.QuizQuestionProcessor
import ru.klekchyan.quizEngine.question_common.QuizQuestionContext
import ru.klekchyan.quizEngine.question_common.helpers.addError
import ru.klekchyan.quizEngine.question_common.helpers.isUpdatableCommand
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionWorkMode
import ru.klekchyan.quizEngine.question_mappers.fromTransport
import ru.klekchyan.quizEngine.question_mappers.toTransport
import ru.klekchyan.quizEngine.question_mappers.toTransportInitQuestions
import ru.klekchyan.quizEngine.questions_api.*

val sessions = mutableSetOf<WebSocketSession>()

suspend fun WebSocketSession.wsHandlerV1(processor: QuizQuestionProcessor) {
    sessions.add(this)

    val ctx = QuizQuestionContext()
    ctx.workMode = QuizQuestionWorkMode.STUB
    processor.exec(ctx)
    val init = questionApiV1ResponseSerialize(ctx.toTransportInitQuestions())
    outgoing.send(Frame.Text(init))

    incoming.receiveAsFlow()
        .filterIsInstance<Frame.Text>()
        .collect {

            val context = QuizQuestionContext()


            try {
                val jsonStr = it.readText()
                val request = questionApiV1RequestDeserialize<IRequestQuestion>(jsonStr)
                context.fromTransport(request)
                processor.exec(context)
                val result = questionApiV1ResponseSerialize(context.toTransport())

                if (context.isUpdatableCommand()) {
                    sessions.forEach { session ->
                        session.send(Frame.Text(result))
                    }
                } else {
                    outgoing.send(Frame.Text(result))
                }
            } catch (e: ClosedReceiveChannelException) {
                sessions.remove(this)
            } catch (e: Throwable) {
                context.addError(e.asQuizError())
                val response = questionApiV1ResponseSerialize(context.toTransportInitQuestions())
                outgoing.send(Frame.Text(response))
            }
        }
}