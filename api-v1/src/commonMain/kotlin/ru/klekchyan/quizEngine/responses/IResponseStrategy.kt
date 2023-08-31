package ru.klekchyan.quizEngine.responses

import kotlinx.serialization.KSerializer
import ru.klekchyan.quizEngine.IApiStrategy
import ru.klekchyan.quizEngine.api.v1.models.IResponse
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
            readGameResponseStrategy,
            updateGameResponseStrategy,
            deleteGameResponseStrategy,
            readAllGamesResponseStrategy,

            createRoundResponseStrategy,
            readRoundResponseStrategy,
            updateRoundResponseStrategy,
            deleteRoundResponseStrategy,
            readAllRoundsResponseStrategy,

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
