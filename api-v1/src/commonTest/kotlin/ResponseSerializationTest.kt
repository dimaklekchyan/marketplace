import ru.klekchyan.quizEngine.apiV1ResponseDeserialize
import ru.klekchyan.quizEngine.apiV1ResponseSerialize
import ru.klekchyan.quizEngine.api.v1.models.IResponse
import ru.klekchyan.quizEngine.api.v1.models.QuestionResponseObject
import ru.klekchyan.quizEngine.api.v1.models.QuestionType
import ru.klekchyan.quizEngine.api.v1.models.QuestionUpdateResponse
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response: IResponse = QuestionUpdateResponse(
        responseType = "updateQuestion",
        requestId = "123",
        question = QuestionResponseObject(
            questionType = QuestionType.MULTIPLE_OPTIONS_QUESTION,
            gameId = 0,
            roundId = 0,
            formulation = "",
            answers = null,
            matchingTerms = null,
            id = 234
        )
    )

    @Test
    fun serialize() {
        val json = apiV1ResponseSerialize(response)
        println(json)

        assertContains(json, Regex("\"questionType\":\\s*\"multipleOptionsQuestion\""))
        assertContains(json, Regex("\"responseType\":\\s*\"updateQuestion\""))
        assertContains(json, Regex("\"id\":\\s*234"))
    }

    @Test
    fun deserialize() {
        val json = apiV1ResponseSerialize(response)
        val obj = apiV1ResponseDeserialize<QuestionUpdateResponse>(json)

        assertEquals(response, obj)
    }
}
