package ru.klekchyan.quizEngine.strategy

import kotlinx.serialization.KSerializer
import ru.klekchyan.quizEngine.core_api.IApiStrategy
import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.reflect.KClass

interface RoundRequestStrategy: IApiStrategy<IRequestRound> {
    companion object {
        inline fun <reified R: IRequestRound> create(
            discriminator: String,
            serializer: KSerializer<out IRequestRound>,
            crossinline discriminatorFiller: (request: R, discriminator: String) -> R
        ): RoundRequestStrategy = object : RoundRequestStrategy {
            override val discriminator: String = discriminator
            override val clazz: KClass<out IRequestRound> = R::class
            override val serializer: KSerializer<out IRequestRound> = serializer
            override fun <T : IRequestRound> fillDiscriminator(req: T): T {
                require(req is R)
                @Suppress("UNCHECKED_CAST")
                return discriminatorFiller(req, discriminator) as T
            }
        }

        private val members: List<RoundRequestStrategy> = listOf(
            createRoundRequestStrategy,
            readRoundRequestStrategy,
            updateRoundRequestStrategy,
            deleteRoundRequestStrategy,
            readAllRoundsRequestStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}

val createRoundRequestStrategy = RoundRequestStrategy.create<RoundCreateRequest>(
    discriminator = "createRound",
    serializer = RoundCreateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readRoundRequestStrategy = RoundRequestStrategy.create<RoundReadRequest>(
    discriminator = "readRound",
    serializer = RoundReadRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val updateRoundRequestStrategy = RoundRequestStrategy.create<RoundUpdateRequest>(
    discriminator = "updateRound",
    serializer = RoundUpdateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val deleteRoundRequestStrategy = RoundRequestStrategy.create<RoundDeleteRequest>(
    discriminator = "deleteRound",
    serializer = RoundDeleteRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readAllRoundsRequestStrategy = RoundRequestStrategy.create<RoundReadAllRequest>(
    discriminator = "readAllRound",
    serializer = RoundReadAllRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

