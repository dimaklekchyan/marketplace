package ru.klekchyan.quizEngine

import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class QuestionRequestSerializationTest {
    private val createQuestionRequest: IRequestQuestion = QuestionCreateRequest(
        requestType = "createQuestion",
        requestId = "123",
        debug = QuestionDebug(
            mode = QuestionRequestDebugMode.STUB,
            stub = QuestionRequestDebugStubs.BAD_ANSWERS
        ),
        question = QuestionCreateObject(
            questionType = QuestionType.MULTIPLE_OPTIONS_QUESTION,
            gameId = "0",
            roundId = "0",
            formulation = "",
            answers = null,
            matchingTerms = null
        )
    )

    @Test
    fun serializeQuestion() {
        val json = questionApiV1RequestSerialize(createQuestionRequest)
        println(json)

        assertContains(json, Regex("\"questionType\":\\s*\"multipleOptionsQuestion\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badAnswers\""))
        assertContains(json, Regex("\"requestType\":\\s*\"createQuestion\""))
    }

    @Test
    fun deserializeQuestion() {
        val json = questionApiV1RequestSerialize(createQuestionRequest)
        println(json)
        val obj = questionApiV1RequestDeserialize<QuestionCreateRequest>(json)

        assertEquals(createQuestionRequest, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {
                "requestType": "createQuestion",
                "requestId": "123",
                "debug": {
                    "mode": "stub",
                    "stub": "badAnswers"
                },
                "question": {
                    "questionType": "multipleOptionsQuestion",
                    "gameId": "0",
                    "roundId": "0",
                    "formulation": "",
                    "answers": null,
                    "matchingTerms": null
                }
            }
        """.trimIndent()

        val obj = questionApiV1RequestDeserialize<QuestionCreateRequest>(jsonString)

        assertEquals(createQuestionRequest, obj)
    }
}
