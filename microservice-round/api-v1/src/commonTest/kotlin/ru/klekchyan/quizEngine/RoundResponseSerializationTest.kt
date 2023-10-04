package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RoundResponseSerializationTest {
    private val response: IResponseRound = RoundUpdateResponse(
        responseType = "updateRound",
        requestId = "123",
        round = RoundResponseObject(
            gameId = "0",
            id = "234",
            title = "Some title",
            description = "Some description"
        )
    )

    @Test
    fun serialize() {
        val json = roundApiV1ResponseSerialize(response)
        println(json)

        assertContains(json, Regex("\"responseType\":\\s*\"updateRound\""))
        assertContains(json, Regex("\"id\":\"234\""))
    }

    @Test
    fun deserialize() {
        val json = roundApiV1ResponseSerialize(response)
        val obj = roundApiV1ResponseDeserialize<RoundUpdateResponse>(json)

        assertEquals(response, obj)
    }
}
