package ru.klekchyan.quizEngine

import kotlin.test.Test
import kotlin.test.assertEquals
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.stubs.QuizStubs
import ru.klekchyan.quizEngine.v1.fromTransport
import ru.klekchyan.quizEngine.v1.toTransport

class MapperTest {
    @Test
    fun fromTransport() {
        val req = GameCreateRequest(
            requestId = "1234",
            debug = GameDebug(
                mode = RequestDebugMode.STUB,
                stub = GameRequestDebugStubs.SUCCESS,
            ),
            game = GameCreateObject(
                title = "title",
                description = "desc"
            ),
        )

        val context = QuizContext()
        context.fromTransport(req)

        assertEquals(QuizStubs.SUCCESS, context.stubCase)
        assertEquals(QuizWorkMode.STUB, context.workMode)
        assertEquals("title", context.gameRequest.title)
        assertEquals("desc", context.gameRequest.description)
    }

    @Test
    fun toTransport() {
        val context = QuizContext(
            requestId = QuizRequestId("1234"),
            command = QuizCommand.CREATE_ROUND,
            roundResponse = QuizRound(
                id = QuizRoundId(1),
                gameId = QuizGameId(0),
                title = "title",
                description = "desc"
            ),
            errors = mutableListOf(
                QuizError(
                    code = "err",
                    group = "request",
                    field = "gameId",
                    message = "wrong game id",
                )
            ),
            state = QuizState.RUNNING,
        )

        val req = context.toTransport() as RoundCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.round?.title)
        assertEquals("desc", req.round?.description)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("gameId", req.errors?.firstOrNull()?.field)
        assertEquals("wrong game id", req.errors?.firstOrNull()?.message)
    }
}