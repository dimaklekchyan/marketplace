package test

import fixture.client.Client
import io.kotest.core.spec.style.FunSpec
import test.actions.createQuestionV1
import test.actions.deleteQuestionV1

internal fun FunSpec.testApiV1(client: Client) {
    context("v1") {
        test("Create question ok") {
            client.createQuestionV1()
        }
        test("Delete question ok") {
            client.deleteQuestionV1()
        }
    }
}