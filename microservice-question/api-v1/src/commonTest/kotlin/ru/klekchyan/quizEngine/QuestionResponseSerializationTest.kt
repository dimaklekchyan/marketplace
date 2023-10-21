package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.questions_api.questionApiV1ResponseDeserialize
import ru.klekchyan.quizEngine.questions_api.questionApiV1ResponseSerialize
import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class QuestionResponseSerializationTest {
    private val response: IResponseQuestion = QuestionUpdateResponse(
        responseType = "updateQuestion",
        requestId = "123",
        question = QuestionResponseObject(
            questionType = QuestionType.MULTIPLE_OPTIONS_QUESTION,
            gameId = "0",
            roundId = "0",
            formulation = "",
            answers = null,
            matchingTerms = null,
            id = "234"
        )
    )

    @Test
    fun serialize() {
        val json = questionApiV1ResponseSerialize(response)
        println(json)

        assertContains(json, Regex("\"questionType\":\\s*\"multipleOptionsQuestion\""))
        assertContains(json, Regex("\"responseType\":\\s*\"updateQuestion\""))
        assertContains(json, Regex("\"id\":\"234\""))
    }

    @Test
    fun deserialize() {
        val json = questionApiV1ResponseSerialize(response)
        val obj = questionApiV1ResponseDeserialize<QuestionUpdateResponse>(json)

        assertEquals(response, obj)
    }
}
