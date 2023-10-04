package ru.klekchyan.quizEngine.strategy

import kotlinx.serialization.KSerializer
import ru.klekchyan.quizEngine.IApiStrategy
import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.reflect.KClass

interface QuestionRequestStrategy: IApiStrategy<IRequestQuestion> {
    companion object {
        inline fun <reified R: IRequestQuestion> create(
            discriminator: String,
            serializer: KSerializer<out IRequestQuestion>,
            crossinline discriminatorFiller: (request: R, discriminator: String) -> R
        ): QuestionRequestStrategy = object : QuestionRequestStrategy {
            override val discriminator: String = discriminator
            override val clazz: KClass<out IRequestQuestion> = R::class
            override val serializer: KSerializer<out IRequestQuestion> = serializer
            override fun <T : IRequestQuestion> fillDiscriminator(req: T): T {
                require(req is R)
                @Suppress("UNCHECKED_CAST")
                return discriminatorFiller(req, discriminator) as T
            }
        }

        private val members: List<QuestionRequestStrategy> = listOf(
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

val createQuestionRequestStrategy = QuestionRequestStrategy.create<QuestionCreateRequest>(
    discriminator = "createQuestion",
    serializer = QuestionCreateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readQuestionRequestStrategy = QuestionRequestStrategy.create<QuestionReadRequest>(
    discriminator = "readQuestion",
    serializer = QuestionReadRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val updateQuestionRequestStrategy = QuestionRequestStrategy.create<QuestionUpdateRequest>(
    discriminator = "updateQuestion",
    serializer = QuestionUpdateRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val deleteQuestionRequestStrategy = QuestionRequestStrategy.create<QuestionDeleteRequest>(
    discriminator = "deleteQuestion",
    serializer = QuestionDeleteRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

val readAllQuestionsRequestStrategy = QuestionRequestStrategy.create<QuestionReadAllRequest>(
    discriminator = "readAllQuestion",
    serializer = QuestionReadAllRequest.serializer(),
    discriminatorFiller = { request, discriminator ->
        request.copy(requestType = discriminator)
    }
)

