package test.actions

import fixture.client.Client
import io.kotest.assertions.withClue
import io.kotest.matchers.should

internal suspend fun Client.deleteQuestionV1(): Unit =
    withClue("createQuestionV1") {
        val response = sendAndReceive(
            path = "question/delete",
            requestBody = """
                {
                    "questionId": 2
                }
            """.trimIndent()
        )

        response should haveNoErrors
    }
