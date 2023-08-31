package ru.klekchyan.quizEngine.requests

import kotlinx.serialization.KSerializer
import ru.klekchyan.quizEngine.IApiStrategy
import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.reflect.KClass

interface IRequestStrategy: IApiStrategy<IRequest> {
    companion object {
        inline fun <reified R: IRequest> create(
            discriminator: String,
            serializer: KSerializer<out IRequest>,
            crossinline discriminatorFiller: (request: R, discriminator: String) -> R
        ): IRequestStrategy = object : IRequestStrategy {
            override val discriminator: String = discriminator
            override val clazz: KClass<out IRequest> = R::class
            override val serializer: KSerializer<out IRequest> = serializer
            override fun <T : IRequest> fillDiscriminator(req: T): T {
                require(req is R)
                @Suppress("UNCHECKED_CAST")
                return discriminatorFiller(req, discriminator) as T
            }
        }

        private val members: List<IRequestStrategy> = listOf(
            createGameRequestStrategy,
            readGameRequestStrategy,
            updateGameRequestStrategy,
            deleteGameRequestStrategy,
            readAllGamesRequestStrategy,

            createQuestionRequestStrategy,
            readQuestionRequestStrategy,
            updateQuestionRequestStrategy,
            deleteQuestionRequestStrategy,
            readAllQuestionsRequestStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
