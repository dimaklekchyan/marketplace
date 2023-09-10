package ru.klekchyan.marketplace.requests

import ru.klekchyan.marketplace.api.v1.models.*

val createGameRequestStrategy = IRequestStrategy.create<GameCreateRequest>(
    discriminator = "createGame",
    serializer = GameCreateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readGameRequestStrategy = IRequestStrategy.create<GameReadRequest>(
    discriminator = "readGame",
    serializer = GameReadRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val updateGameRequestStrategy = IRequestStrategy.create<GameUpdateRequest>(
    discriminator = "updateGame",
    serializer = GameUpdateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val deleteGameRequestStrategy = IRequestStrategy.create<GameDeleteRequest>(
    discriminator = "deleteGame",
    serializer = GameDeleteRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readAllGamesRequestStrategy = IRequestStrategy.create<GameReadAllRequest>(
    discriminator = "readAllGame",
    serializer = GameReadAllRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)
