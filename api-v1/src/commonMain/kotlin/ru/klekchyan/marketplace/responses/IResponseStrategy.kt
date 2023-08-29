package ru.klekchyan.marketplace.responses

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.IApiStrategy
import ru.klekchyan.marketplace.api.v1.models.IResponse
import kotlin.reflect.KClass

interface IResponseStrategy: IApiStrategy<IResponse> {
    companion object {
        inline fun <reified R: IResponse> create(
            discriminator: String,
            serializer: KSerializer<out IResponse>,
            crossinline discriminatorFiller: (response: R, discriminator: String) -> R
        ): IResponseStrategy = object : IResponseStrategy {
            override val discriminator: String = discriminator
            override val clazz: KClass<out IResponse> = R::class
            override val serializer: KSerializer<out IResponse> = serializer
            override fun <T : IResponse> fillDiscriminator(req: T): T {
                require(req is R)
                @Suppress("UNCHECKED_CAST")
                return discriminatorFiller(req, discriminator) as T
            }
        }

        val members: List<IResponseStrategy> = listOf(
            createGameResponseStrategy,
            readAllGamesResponseStrategy,
            updateGameResponseStrategy,
            deleteGameResponseStrategy,
            readAllGamesResponseStrategy,

            createQuestionResponseStrategy,
            readQuestionResponseStrategy,
            updateQuestionResponseStrategy,
            deleteQuestionResponseStrategy,
            readAllQuestionsResponseStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
