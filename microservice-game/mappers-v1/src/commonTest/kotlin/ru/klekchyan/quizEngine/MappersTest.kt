package ru.klekchyan.quizEngine

import models.QuizCommonEntityId
import models.QuizError
import models.QuizRequestId
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.stubs.QuizGameStubs
import ru.klekchyan.quizEngine.v1.fromTransport
import ru.klekchyan.quizEngine.v1.toTransport
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = GameCreateRequest(
            requestId = "1234",
            debug = GameDebug(
                mode = GameRequestDebugMode.STUB,
                stub = GameRequestDebugStubs.SUCCESS,
            ),
            game = GameCreateObject(
                title = "title",
                description = "description"
            ),
        )

        val context = QuizGameContext()
        context.fromTransport(req)

        assertEquals(QuizGameStubs.SUCCESS, context.stubCase)
        assertEquals(QuizGameWorkMode.STUB, context.workMode)
        assertEquals("description", context.gameRequest.description)
        assertEquals("title", context.gameRequest.title)
    }

    @Test
    fun toTransport() {
        val context = QuizGameContext(
            requestId = QuizRequestId("1234"),
            command = QuizGameCommand.CREATE,
            gameResponse = QuizGame(
                id = QuizCommonEntityId("1"),
                title = "title",
                description = "description"
            ),
            errors = mutableListOf(
                QuizError(
                    code = "err",
                    group = "request",
                    field = "gameId",
                    message = "wrong game id",
                )
            ),
            state = QuizGameState.RUNNING,
        )

        val req = context.toTransport() as GameCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.game?.title)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("gameId", req.errors?.firstOrNull()?.field)
        assertEquals("wrong game id", req.errors?.firstOrNull()?.message)
    }
}