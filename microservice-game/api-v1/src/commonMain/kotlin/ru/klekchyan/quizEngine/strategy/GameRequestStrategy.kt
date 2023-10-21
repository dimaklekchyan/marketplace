package ru.klekchyan.quizEngine.strategy

import kotlinx.serialization.KSerializer
import ru.klekchyan.quizEngine.core_api.IApiStrategy
import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.reflect.KClass

interface GameRequestStrategy: IApiStrategy<IRequestGame> {
    companion object {
        inline fun <reified R: IRequestGame> create(
            discriminator: String,
            serializer: KSerializer<out IRequestGame>,
            crossinline discriminatorFiller: (request: R, discriminator: String) -> R
        ): GameRequestStrategy = object : GameRequestStrategy {
            override val discriminator: String = discriminator
            override val clazz: KClass<out IRequestGame> = R::class
            override val serializer: KSerializer<out IRequestGame> = serializer
            override fun <T : IRequestGame> fillDiscriminator(req: T): T {
                require(req is R)
                @Suppress("UNCHECKED_CAST")
                return discriminatorFiller(req, discriminator) as T
            }
        }

        private val members: List<GameRequestStrategy> = listOf(
            createGameRequestStrategy,
            readGameRequestStrategy,
            updateGameRequestStrategy,
            deleteGameRequestStrategy,
            readAllGamesRequestStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}

val createGameRequestStrategy = GameRequestStrategy.create<GameCreateRequest>(
    discriminator = "createGame",
    serializer = GameCreateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readGameRequestStrategy = GameRequestStrategy.create<GameReadRequest>(
    discriminator = "readGame",
    serializer = GameReadRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val updateGameRequestStrategy = GameRequestStrategy.create<GameUpdateRequest>(
    discriminator = "updateGame",
    serializer = GameUpdateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val deleteGameRequestStrategy = GameRequestStrategy.create<GameDeleteRequest>(
    discriminator = "deleteGame",
    serializer = GameDeleteRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readAllGamesRequestStrategy = GameRequestStrategy.create<GameReadAllRequest>(
    discriminator = "readAllGame",
    serializer = GameReadAllRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

