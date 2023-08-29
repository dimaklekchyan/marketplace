import ru.klekchyan.marketplace.apiV1RequestDeserialize
import ru.klekchyan.marketplace.apiV1RequestSerialize
import ru.klekchyan.marketplace.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val createQuestionRequest: IRequest = QuestionCreateRequest(
        requestType = "createQuestion",
        requestId = "123",
        debug = QuestionDebug(
            mode = RequestDebugMode.STUB,
            stub = QuestionRequestDebugStubs.BAD_ANSWERS
        ),
        question = BaseQuestion(
            questionType = QuestionType.MULTIPLE_OPTIONS_QUESTION,
            gameId = 0,
            roundId = 0,
            formulation = "",
            answers = null,
            matchingTerms = null
        )
    )

    private val createGameRequest: IRequest = GameCreateRequest(
        requestType = "createGame",
        requestId = "123",
        debug = GameDebug(
            mode = RequestDebugMode.STUB,
            stub = GameRequestDebugStubs.SUCCESS
        ),
        game = BaseGame(
            title = "Game",
            description = "description"
        )
    )

    @Test
    fun serializeQuestion() {
        val json = apiV1RequestSerialize(createQuestionRequest)
        println(json)

        assertContains(json, Regex("\"questionType\":\\s*\"multipleOptionsQuestion\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badAnswers\""))
        assertContains(json, Regex("\"requestType\":\\s*\"createQuestion\""))
    }
    @Test
    fun serializeGamen() {
        val json = apiV1RequestSerialize(createGameRequest)
        println(json)

        assertContains(json, Regex("\"title\":\\s*\"Game\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"requestType\":\\s*\"createGame\""))
    }

    @Test
    fun deserializeQuestion() {
        val json = apiV1RequestSerialize(createQuestionRequest)
        println(json)
        val obj = apiV1RequestDeserialize<QuestionCreateRequest>(json)

        assertEquals(createQuestionRequest, obj)
    }

    @Test
    fun deserializeGame() {
        val json = apiV1RequestSerialize(createGameRequest)
        println(json)
        val obj = apiV1RequestDeserialize<GameCreateRequest>(json)

        assertEquals(createGameRequest, obj)
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

        assertEquals(createQuestionRequest, obj)
    }
}
