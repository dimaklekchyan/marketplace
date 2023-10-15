package ru.klekchyan.quizEngine.strategy

import kotlinx.serialization.KSerializer
import ru.klekchyan.quizEngine.core_api.IApiStrategy
import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.reflect.KClass

interface RoundResponseStrategy: IApiStrategy<IResponseRound> {
    companion object {
        inline fun <reified R: IResponseRound> create(
            discriminator: String,
            serializer: KSerializer<out IResponseRound>,
            crossinline discriminatorFiller: (response: R, discriminator: String) -> R
        ): RoundResponseStrategy = object : RoundResponseStrategy {
            override val discriminator: String = discriminator
            override val clazz: KClass<out IResponseRound> = R::class
            override val serializer: KSerializer<out IResponseRound> = serializer
            override fun <T : IResponseRound> fillDiscriminator(req: T): T {
                require(req is R)
                @Suppress("UNCHECKED_CAST")
                return discriminatorFiller(req, discriminator) as T
            }
        }

        val members: List<RoundResponseStrategy> = listOf(
            createRoundResponseStrategy,
            readRoundResponseStrategy,
            updateRoundResponseStrategy,
            deleteRoundResponseStrategy,
            readAllRoundsResponseStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}

val createRoundResponseStrategy = RoundResponseStrategy.create<RoundCreateResponse>(
    discriminator = "createRound",
    serializer = RoundCreateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readRoundResponseStrategy = RoundResponseStrategy.create<RoundReadResponse>(
    discriminator = "readRound",
    serializer = RoundReadResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val updateRoundResponseStrategy = RoundResponseStrategy.create<RoundUpdateResponse>(
    discriminator = "updateRound",
    serializer = RoundUpdateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val deleteRoundResponseStrategy = RoundResponseStrategy.create<RoundDeleteResponse>(
    discriminator = "deleteRound",
    serializer = RoundDeleteResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readAllRoundsResponseStrategy = RoundResponseStrategy.create<RoundReadAllResponse>(
    discriminator = "readAllRound",
    serializer = RoundReadAllResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)