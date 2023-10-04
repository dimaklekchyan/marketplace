package ru.klekchyan.quizEngine

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.klekchyan.quizEngine.api.v1.models.*
import ru.klekchyan.quizEngine.strategy.RoundRequestStrategy

val roundRequestSerializer = RoundRequestSerializer(RoundRequestSerializerBase)

private object RoundRequestSerializerBase : JsonContentPolymorphicSerializer<IRequestRound>(IRequestRound::class) {
    private const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IRequestRound> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return RoundRequestStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IRequest::class} implementation"
            )
    }
}

class RoundRequestSerializer<T : IRequestRound>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        RoundRequestStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IRequest instance in RequestSerializer"
            )
}
