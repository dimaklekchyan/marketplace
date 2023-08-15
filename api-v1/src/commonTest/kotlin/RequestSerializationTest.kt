import ru.klekchyan.marketplace.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request: IRequest = QuestionCreateRequest(
        requestType = "createQuestion",
        requestId = "123",
        debug = QuestionDebug(
            mode = QuestionRequestDebugMode.STUB,
            stub = QuestionRequestDebugStubs.BAD_ANSWERS
        ),
        question = QuestionCreateObject(
            questionType = QuestionType.MULTIPLE_OPTIONS_QUESTION,
            gameId = 0,
            roundId = 0,
            formulation = "",
            answers = null,
            matchingTerms = null
        )
    )

    @Test
    fun serialize() {
        val json = apiV1RequestSerialize(request)
        println(json)

        assertContains(json, Regex("\"questionType\":\\s*\"multipleOptionsQuestion\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badAnswers\""))
        assertContains(json, Regex("\"requestType\":\\s*\"createQuestion\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1RequestSerialize(request)
        println(json)
        val obj = apiV1RequestDeserialize<QuestionCreateRequest>(json)

        assertEquals(request, obj)
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
                    "gameId": 0,
                    "roundId": 0,
                    "formulation": "",
                    "answers": null,
                    "matchingTerms": null
                }
            }
        """.trimIndent()

        val obj = apiV1RequestDeserialize<QuestionCreateRequest>(jsonString)

        assertEquals(request, obj)
    }
}
