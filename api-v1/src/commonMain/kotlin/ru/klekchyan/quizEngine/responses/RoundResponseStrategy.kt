package ru.klekchyan.quizEngine.responses

import ru.klekchyan.quizEngine.api.v1.models.*

val createRoundResponseStrategy = IResponseStrategy.create<RoundCreateResponse>(
    discriminator = "createRound",
    serializer = RoundCreateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readRoundResponseStrategy = IResponseStrategy.create<RoundReadResponse>(
    discriminator = "readRound",
    serializer = RoundReadResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val updateRoundResponseStrategy = IResponseStrategy.create<RoundUpdateResponse>(
    discriminator = "updateRound",
    serializer = RoundUpdateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val deleteRoundResponseStrategy = IResponseStrategy.create<RoundDeleteResponse>(
    discriminator = "deleteRound",
    serializer = RoundDeleteResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readAllRoundsResponseStrategy = IResponseStrategy.create<RoundReadAllResponse>(
    discriminator = "readAllRound",
    serializer = RoundReadAllResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)