package ru.klekchyan.quizEngine.stubs

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.gameApiV1Mapper
import kotlin.test.assertEquals

class V1GameStubApiTest {

    @Test
    fun create() = testApplication {
        val response = client.post("games/v1/create") {
            val requestObj = GameCreateRequest(
                requestId = "12345",
                game = GameCreateObject(
                    title = "title",
                    description = "description"
                ),
                debug = GameDebug(
                    mode = GameRequestDebugMode.STUB,
                    stub = GameRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = gameApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = gameApiV1Mapper.decodeFromString<GameCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.game?.id)
    }

    @Test
    fun read() = testApplication {
        val response = client.post("games/v1/read") {
            val requestObj = GameReadRequest(
                requestId = "12345",
                game = GameReadObject(
                    id = "1"
                ),
                debug = GameDebug(
                    mode = GameRequestDebugMode.STUB,
                    stub = GameRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = gameApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = gameApiV1Mapper.decodeFromString<GameReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.game?.id)
    }

    @Test
    fun update() = testApplication {
        val response = client.post("games/v1/update") {
            val requestObj = GameUpdateRequest(
                requestId = "12345",
                game = GameUpdateObject(
                    //...
                ),
                debug = GameDebug(
                    mode = GameRequestDebugMode.STUB,
                    stub = GameRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = gameApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = gameApiV1Mapper.decodeFromString<GameUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.game?.id)
    }

    @Test
    fun delete() = testApplication {
        val response = client.post("games/v1/delete") {
            val requestObj = GameDeleteRequest(
                requestId = "12345",
                game = GameDeleteObject(
                    //...
                ),
                debug = GameDebug(
                    mode = GameRequestDebugMode.STUB,
                    stub = GameRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = gameApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = gameApiV1Mapper.decodeFromString<GameDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.game?.id)
    }

    @Test
    fun readAll() = testApplication {
        val response = client.post("games/v1/readAll") {
            val requestObj = GameReadAllRequest(
                requestId = "12345",
                debug = GameDebug(
                    mode = GameRequestDebugMode.STUB,
                    stub = GameRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = gameApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = gameApiV1Mapper.decodeFromString<GameReadAllResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(3, responseObj.games?.size)
    }
}
