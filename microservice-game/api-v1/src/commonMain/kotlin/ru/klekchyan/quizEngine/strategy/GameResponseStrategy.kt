package ru.klekchyan.quizEngine.strategy

import kotlinx.serialization.KSerializer
import ru.klekchyan.quizEngine.core_api.IApiStrategy
import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.reflect.KClass

interface GameResponseStrategy: IApiStrategy<IResponseGame> {
    companion object {
        inline fun <reified R: IResponseGame> create(
            discriminator: String,
            serializer: KSerializer<out IResponseGame>,
            crossinline discriminatorFiller: (response: R, discriminator: String) -> R
        ): GameResponseStrategy = object : GameResponseStrategy {
            override val discriminator: String = discriminator
            override val clazz: KClass<out IResponseGame> = R::class
            override val serializer: KSerializer<out IResponseGame> = serializer
            override fun <T : IResponseGame> fillDiscriminator(req: T): T {
                require(req is R)
                @Suppress("UNCHECKED_CAST")
                return discriminatorFiller(req, discriminator) as T
            }
        }

        val members: List<GameResponseStrategy> = listOf(
            createGameResponseStrategy,
            readGameResponseStrategy,
            updateGameResponseStrategy,
            deleteGameResponseStrategy,
            readAllGamesResponseStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}

val createGameResponseStrategy = GameResponseStrategy.create<GameCreateResponse>(
    discriminator = "createGame",
    serializer = GameCreateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readGameResponseStrategy = GameResponseStrategy.create<GameReadResponse>(
    discriminator = "readGame",
    serializer = GameReadResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val updateGameResponseStrategy = GameResponseStrategy.create<GameUpdateResponse>(
    discriminator = "updateGame",
    serializer = GameUpdateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val deleteGameResponseStrategy = GameResponseStrategy.create<GameDeleteResponse>(
    discriminator = "deleteGame",
    serializer = GameDeleteResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readAllGamesResponseStrategy = GameResponseStrategy.create<GameReadAllResponse>(
    discriminator = "readAllGame",
    serializer = GameReadAllResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)