package test.actions

import fixture.client.Client
import io.kotest.assertions.withClue
import io.kotest.matchers.should

internal suspend fun Client.createQuestionV1(): Unit =
    withClue("createQuestionV1") {
        val response = sendAndReceive(
            path = "question/create",
            requestBody = """
                {
                    "formulation": "The capital of the UK is this?"
                }
            """.trimIndent()
        )

        response should haveNoErrors
    }
