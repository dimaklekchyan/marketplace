package ru.klekchyan.quizEngine.responses

import ru.klekchyan.quizEngine.api.v1.models.*

val createQuestionResponseStrategy = IResponseStrategy.create<QuestionCreateResponse>(
    discriminator = "createQuestion",
    serializer = QuestionCreateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readQuestionResponseStrategy = IResponseStrategy.create<QuestionReadResponse>(
    discriminator = "readQuestion",
    serializer = QuestionReadResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val updateQuestionResponseStrategy = IResponseStrategy.create<QuestionUpdateResponse>(
    discriminator = "updateQuestion",
    serializer = QuestionUpdateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val deleteQuestionResponseStrategy = IResponseStrategy.create<QuestionDeleteResponse>(
    discriminator = "deleteQuestion",
    serializer = QuestionDeleteResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readAllQuestionsResponseStrategy = IResponseStrategy.create<QuestionReadAllResponse>(
    discriminator = "readAllQuestion",
    serializer = QuestionReadAllResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)