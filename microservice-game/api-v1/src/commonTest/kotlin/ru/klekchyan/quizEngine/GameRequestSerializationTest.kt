package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class GameRequestSerializationTest {
    private val createGameRequest: IRequestGame = GameCreateRequest(
        requestType = "createGame",
        requestId = "123",
        debug = GameDebug(
            mode = GameRequestDebugMode.STUB,
            stub = GameRequestDebugStubs.BAD_TITLE
        ),
        game = GameCreateObject(
            title = "Title",
            description = "Description"
        )
    )

    @Test
    fun serializeGame() {
        val json = gameApiV1RequestSerialize(createGameRequest)
        println(json)

        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"title\":\\s*\"Title\""))
    }

    @Test
    fun deserializeGame() {
        val json = gameApiV1RequestSerialize(createGameRequest)
        println(json)
        val obj = gameApiV1RequestDeserialize<GameCreateRequest>(json)

        assertEquals(createGameRequest, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {
                "requestType": "createGame",
                "requestId": "123",
                "debug": {
                    "mode": "stub",
                    "stub": "badTitle"
                },
                "game": {
                    "title": "Title",
                    "description": "Description"
                }
            }
        """.trimIndent()

        val obj = gameApiV1RequestDeserialize<GameCreateRequest>(jsonString)

        println("object: $obj")

        assertEquals(createGameRequest, obj)
    }
}
