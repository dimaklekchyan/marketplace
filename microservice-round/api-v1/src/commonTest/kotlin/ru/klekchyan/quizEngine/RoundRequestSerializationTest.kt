package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RoundRequestSerializationTest {
    private val createRoundRequest: IRequestRound = RoundCreateRequest(
        requestType = "createRound",
        requestId = "123",
        debug = RoundDebug(
            mode = RoundRequestDebugMode.STUB,
            stub = RoundRequestDebugStubs.BAD_ID
        ),
        round = RoundCreateObject(
            gameId = "0",
            title = "Some title",
            description = "Some description"
        )
    )

    @Test
    fun serializeRound() {
        val json = roundApiV1RequestSerialize(createRoundRequest)
        println(json)

        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badId\""))
        assertContains(json, Regex("\"requestType\":\\s*\"createRound\""))
    }

    @Test
    fun deserializeRound() {
        val json = roundApiV1RequestSerialize(createRoundRequest)
        println(json)
        val obj = roundApiV1RequestDeserialize<RoundCreateRequest>(json)

        assertEquals(createRoundRequest, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {
                "requestType": "createRound",
                "requestId": "123",
                "debug": {
                    "mode": "stub",
                    "stub": "badId"
                },
                "round": {
                    "gameId": "0",
                    "title": "Some title",
                    "description": "Some description"
                }
            }
        """.trimIndent()

        val obj = roundApiV1RequestDeserialize<RoundCreateRequest>(jsonString)

        assertEquals(createRoundRequest, obj)
    }
}
