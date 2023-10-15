package ru.klekchyan.quizEngine.questions_api.strategy

import kotlinx.serialization.KSerializer
import ru.klekchyan.quizEngine.questions_api.IApiStrategy
import ru.klekchyan.quizEngine.api.v1.models.*
import kotlin.reflect.KClass

interface QuestionResponseStrategy: IApiStrategy<IResponseQuestion> {
    companion object {
        inline fun <reified R: IResponseQuestion> create(
            discriminator: String,
            serializer: KSerializer<out IResponseQuestion>,
            crossinline discriminatorFiller: (response: R, discriminator: String) -> R
        ): QuestionResponseStrategy = object : QuestionResponseStrategy {
            override val discriminator: String = discriminator
            override val clazz: KClass<out IResponseQuestion> = R::class
            override val serializer: KSerializer<out IResponseQuestion> = serializer
            override fun <T : IResponseQuestion> fillDiscriminator(req: T): T {
                require(req is R)
                @Suppress("UNCHECKED_CAST")
                return discriminatorFiller(req, discriminator) as T
            }
        }

        val members: List<QuestionResponseStrategy> = listOf(
            createQuestionResponseStrategy,
            readQuestionResponseStrategy,
            updateQuestionResponseStrategy,
            deleteQuestionResponseStrategy,
            readAllQuestionsResponseStrategy,
            initQuestionsResponseStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}

val createQuestionResponseStrategy = QuestionResponseStrategy.create<QuestionCreateResponse>(
    discriminator = "createQuestion",
    serializer = QuestionCreateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readQuestionResponseStrategy = QuestionResponseStrategy.create<QuestionReadResponse>(
    discriminator = "readQuestion",
    serializer = QuestionReadResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val updateQuestionResponseStrategy = QuestionResponseStrategy.create<QuestionUpdateResponse>(
    discriminator = "updateQuestion",
    serializer = QuestionUpdateResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val deleteQuestionResponseStrategy = QuestionResponseStrategy.create<QuestionDeleteResponse>(
    discriminator = "deleteQuestion",
    serializer = QuestionDeleteResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val readAllQuestionsResponseStrategy = QuestionResponseStrategy.create<QuestionReadAllResponse>(
    discriminator = "readAllQuestion",
    serializer = QuestionReadAllResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)

val initQuestionsResponseStrategy = QuestionResponseStrategy.create<QuestionInitResponse>(
    discriminator = "initQuestion",
    serializer = QuestionInitResponse.serializer(),
    discriminatorFiller = { response, discriminator ->
        response.copy(responseType = discriminator)
    }
)