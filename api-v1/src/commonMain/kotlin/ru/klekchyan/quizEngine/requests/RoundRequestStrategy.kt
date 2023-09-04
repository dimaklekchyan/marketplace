package ru.klekchyan.quizEngine.requests

import ru.klekchyan.quizEngine.api.v1.models.*

val createRoundRequestStrategy = IRequestStrategy.create<RoundCreateRequest>(
    discriminator = "createRound",
    serializer = RoundCreateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readRoundRequestStrategy = IRequestStrategy.create<RoundReadRequest>(
    discriminator = "readRound",
    serializer = RoundReadRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val updateRoundRequestStrategy = IRequestStrategy.create<RoundUpdateRequest>(
    discriminator = "updateRound",
    serializer = RoundUpdateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val deleteRoundRequestStrategy = IRequestStrategy.create<RoundDeleteRequest>(
    discriminator = "deleteRound",
    serializer = RoundDeleteRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readAllRoundsRequestStrategy = IRequestStrategy.create<RoundReadAllRequest>(
    discriminator = "readAllRound",
    serializer = RoundReadAllRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)
