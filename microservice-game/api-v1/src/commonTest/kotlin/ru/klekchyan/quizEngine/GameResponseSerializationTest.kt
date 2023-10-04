package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class GameResponseSerializationTest {
    private val response: IResponseGame = GameUpdateResponse(
        responseType = "updateGame",
        requestId = "123",
        game = GameResponseObject(
            title = "Title",
            description = "Description",
            id = "234"
        )
    )

    @Test
    fun serialize() {
        val json = gameApiV1ResponseSerialize(response)
        println(json)

        assertContains(json, Regex("\"responseType\":\\s*\"updateGame\""))
        assertContains(json, Regex("\"id\":\"234\""))
    }

    @Test
    fun deserialize() {
        val json = gameApiV1ResponseSerialize(response)
        val obj = gameApiV1ResponseDeserialize<GameUpdateResponse>(json)

        assertEquals(response, obj)
    }
}
