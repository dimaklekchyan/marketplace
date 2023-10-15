package ru.klekchyan.quizEngine.questions_api

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.klekchyan.quizEngine.api.v1.models.IResponse
import ru.klekchyan.quizEngine.api.v1.models.IResponseQuestion
import ru.klekchyan.quizEngine.questions_api.strategy.QuestionResponseStrategy

val questionResponseSerializer = QuestionResponseSerializer(QuestionResponseSerializerBase)

private object QuestionResponseSerializerBase : JsonContentPolymorphicSerializer<IResponseQuestion>(IResponseQuestion::class) {
    private const val discriminator = "responseType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IResponseQuestion> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return QuestionResponseStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IResponse::class} implementation"
            )
    }
}

class QuestionResponseSerializer<T : IResponseQuestion>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        QuestionResponseStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IResponse instance in ResponseSerializer"
            )
}
