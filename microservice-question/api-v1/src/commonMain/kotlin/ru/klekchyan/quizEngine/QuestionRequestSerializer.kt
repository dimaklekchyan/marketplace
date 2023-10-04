package ru.klekchyan.quizEngine

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.strategy.QuestionRequestStrategy

val questionRequestSerializer = QuestionRequestSerializer(QuestionRequestSerializerBase)

private object QuestionRequestSerializerBase : JsonContentPolymorphicSerializer<IRequestQuestion>(IRequestQuestion::class) {
    private const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IRequestQuestion> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return QuestionRequestStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IRequest::class} implementation"
            )
    }
}

class QuestionRequestSerializer<T : IRequestQuestion>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        QuestionRequestStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IRequest instance in RequestSerializer"
            )
}
