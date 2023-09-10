package ru.klekchyan.quizEngine.requests

import ru.klekchyan.quizEngine.api.v1.models.*

val createQuestionRequestStrategy = IRequestStrategy.create<QuestionCreateRequest>(
    discriminator = "createQuestion",
    serializer = QuestionCreateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readQuestionRequestStrategy = IRequestStrategy.create<QuestionReadRequest>(
    discriminator = "readQuestion",
    serializer = QuestionReadRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val updateQuestionRequestStrategy = IRequestStrategy.create<QuestionUpdateRequest>(
    discriminator = "updateQuestion",
    serializer = QuestionUpdateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val deleteQuestionRequestStrategy = IRequestStrategy.create<QuestionDeleteRequest>(
    discriminator = "deleteQuestion",
    serializer = QuestionDeleteRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readAllQuestionsRequestStrategy = IRequestStrategy.create<QuestionReadAllRequest>(
    discriminator = "readAllQuestion",
    serializer = QuestionReadAllRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)
