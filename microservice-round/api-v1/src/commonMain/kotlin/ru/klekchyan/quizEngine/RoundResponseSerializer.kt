package ru.klekchyan.quizEngine

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.klekchyan.quizEngine.api.v1.models.IResponse
import ru.klekchyan.quizEngine.api.v1.models.IResponseRound
import ru.klekchyan.quizEngine.strategy.RoundResponseStrategy


val roundResponseSerializer = RoundResponseSerializer(RoundResponseSerializerBase)

private object RoundResponseSerializerBase : JsonContentPolymorphicSerializer<IResponseRound>(IResponseRound::class) {
    private const val discriminator = "responseType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IResponseRound> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return RoundResponseStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IResponse::class} implementation"
            )
    }
}

class RoundResponseSerializer<T : IResponseRound>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        RoundResponseStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IResponse instance in ResponseSerializer"
            )
}
