package ru.klekchyan.quizEngine.responses

import ru.klekchyan.quizEngine.api.v1.models.*

val createGameResponseStrategy = IResponseStrategy.create<GameCreateResponse>(
    discriminator = "createGame",
    serializer = GameCreateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readGameResponseStrategy = IResponseStrategy.create<GameReadResponse>(
    discriminator = "readGame",
    serializer = GameReadResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val updateGameResponseStrategy = IResponseStrategy.create<GameUpdateResponse>(
    discriminator = "updateGame",
    serializer = GameUpdateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val deleteGameResponseStrategy = IResponseStrategy.create<GameDeleteResponse>(
    discriminator = "deleteGame",
    serializer = GameDeleteResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readAllGamesResponseStrategy = IResponseStrategy.create<GameReadAllResponse>(
    discriminator = "readAllGame",
    serializer = GameReadAllResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)