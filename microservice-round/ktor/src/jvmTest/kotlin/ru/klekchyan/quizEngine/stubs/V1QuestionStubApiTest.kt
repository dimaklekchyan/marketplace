package ru.klekchyan.quizEngine.stubs

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.roundApiV1Mapper
import kotlin.test.assertEquals

class V1RoundStubApiTest {

    @Test
    fun create() = testApplication {
        val response = client.post("rounds/v1/create") {
            val requestObj = RoundCreateRequest(
                requestId = "12345",
                round = RoundCreateObject(
                    gameId = "999",
                    title = "title",
                    description = "description"
                ),
                debug = RoundDebug(
                    mode = RoundRequestDebugMode.STUB,
                    stub = RoundRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = roundApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = roundApiV1Mapper.decodeFromString<RoundCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.round?.id)
    }

    @Test
    fun read() = testApplication {
        val response = client.post("rounds/v1/read") {
            val requestObj = RoundReadRequest(
                requestId = "12345",
                round = RoundReadObject(
                    id = "1"
                ),
                debug = RoundDebug(
                    mode = RoundRequestDebugMode.STUB,
                    stub = RoundRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = roundApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = roundApiV1Mapper.decodeFromString<RoundReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.round?.id)
    }

    @Test
    fun update() = testApplication {
        val response = client.post("rounds/v1/update") {
            val requestObj = RoundUpdateRequest(
                requestId = "12345",
                round = RoundUpdateObject(
                    //...
                ),
                debug = RoundDebug(
                    mode = RoundRequestDebugMode.STUB,
                    stub = RoundRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = roundApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = roundApiV1Mapper.decodeFromString<RoundUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.round?.id)
    }

    @Test
    fun delete() = testApplication {
        val response = client.post("rounds/v1/delete") {
            val requestObj = RoundDeleteRequest(
                requestId = "12345",
                round = RoundDeleteObject(
                    //...
                ),
                debug = RoundDebug(
                    mode = RoundRequestDebugMode.STUB,
                    stub = RoundRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = roundApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = roundApiV1Mapper.decodeFromString<RoundDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.round?.id)
    }

    @Test
    fun readAll() = testApplication {
        val response = client.post("rounds/v1/readAll") {
            val requestObj = RoundReadAllRequest(
                requestId = "12345",
                selector = RoundsSelector(
                    gameId = "1"
                ),
                debug = RoundDebug(
                    mode = RoundRequestDebugMode.STUB,
                    stub = RoundRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = roundApiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = roundApiV1Mapper.decodeFromString<RoundReadAllResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(3, responseObj.rounds?.size)
    }
}
