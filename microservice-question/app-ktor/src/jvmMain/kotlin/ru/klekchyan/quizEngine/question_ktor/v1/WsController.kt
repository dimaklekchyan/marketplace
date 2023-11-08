package ru.klekchyan.quizEngine.question_ktor.v1

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import ru.klekchyan.quizEngine.api.v1.models.IRequestQuestion
import ru.klekchyan.quizEngine.app_common.controllerHelper
import ru.klekchyan.quizEngine.question_common.helpers.isUpdatableCommand
import ru.klekchyan.quizEngine.question_common.models.QuizQuestionCommand
import ru.klekchyan.quizEngine.question_ktor.QuizQuestionAppSettings
import ru.klekchyan.quizEngine.question_mappers.fromTransport
import ru.klekchyan.quizEngine.question_mappers.toTransport
import ru.klekchyan.quizEngine.question_mappers.toTransportInitQuestions
import ru.klekchyan.quizEngine.questions_api.questionApiV1RequestDeserialize
import ru.klekchyan.quizEngine.questions_api.questionApiV1ResponseSerialize
import kotlin.reflect.KClass

val sessions = mutableSetOf<WebSocketSession>()

private val clazz: KClass<*> = WebSocketSession::wsHandlerV1::class
suspend fun WebSocketSession.wsHandlerV1(appSettings: QuizQuestionAppSettings) {
    sessions.add(this)

    appSettings.controllerHelper(
        { command = QuizQuestionCommand.INIT },
        {
            val init = questionApiV1ResponseSerialize(toTransportInitQuestions())
            outgoing.send(Frame.Text(init))
        },
        clazz,
        "wsHandlerV1-init"
    )

    incoming.receiveAsFlow().mapNotNull { it ->
            val frame = it as? Frame.Text ?: return@mapNotNull

            try {
                appSettings.controllerHelper(
                    { fromTransport(questionApiV1RequestDeserialize<IRequestQuestion>(frame.readText())) },
                    {
                        val result = questionApiV1ResponseSerialize(toTransport())
                        // If change request, response is sent to everyone
                        if (isUpdatableCommand()) {
                            sessions.forEach {
                                if (it.isActive) it.send(Frame.Text(result))
                            }
                        } else {
                            outgoing.send(Frame.Text(result))
                        }
                    },
                    clazz,
                    "wsHandlerV1-message"
                )
            } catch (_: ClosedReceiveChannelException) {
                sessions.clear()
            } catch (e: Throwable) {
                println("FFF")
            }
            // Handle finish request
            appSettings.controllerHelper(
                { command = QuizQuestionCommand.FINISH },
                { },
                clazz,
                "wsHandlerV1-finish"
            )
        }.collect()
}