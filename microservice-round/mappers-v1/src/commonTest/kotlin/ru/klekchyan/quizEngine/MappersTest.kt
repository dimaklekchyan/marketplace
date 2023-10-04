package ru.klekchyan.quizEngine

import models.QuizCommonEntityId
import models.QuizError
import models.QuizRequestId
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.models.*
import ru.klekchyan.quizEngine.stubs.QuizRoundStubs
import ru.klekchyan.quizEngine.v1.fromTransport
import ru.klekchyan.quizEngine.v1.toTransport
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = RoundCreateRequest(
            requestId = "1234",
            debug = RoundDebug(
                mode = RoundRequestDebugMode.STUB,
                stub = RoundRequestDebugStubs.SUCCESS,
            ),
            round = RoundCreateObject(
                gameId = "0",
                title = "title",
                description = "description"
            ),
        )

        val context = QuizRoundContext()
        context.fromTransport(req)

        assertEquals(QuizRoundStubs.SUCCESS, context.stubCase)
        assertEquals(QuizRoundWorkMode.STUB, context.workMode)
        assertEquals("description", context.roundRequest.description)
        assertEquals("title", context.roundRequest.title)
    }

    @Test
    fun toTransport() {
        val context = QuizRoundContext(
            requestId = QuizRequestId("1234"),
            command = QuizRoundCommand.CREATE,
            roundResponse = QuizRound(
                id = QuizCommonEntityId("1"),
                gameId = QuizCommonEntityId("0"),
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
            state = QuizRoundState.RUNNING,
        )

        val req = context.toTransport() as RoundCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.round?.title)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("gameId", req.errors?.firstOrNull()?.field)
        assertEquals("wrong game id", req.errors?.firstOrNull()?.message)
    }
}