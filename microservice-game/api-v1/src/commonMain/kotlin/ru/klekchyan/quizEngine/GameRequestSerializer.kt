package ru.klekchyan.quizEngine

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.klekchyan.quizEngine.api.v1.models.IRequest
import ru.klekchyan.quizEngine.api.v1.models.IRequestGame
import ru.klekchyan.quizEngine.strategy.GameRequestStrategy

val gameRequestSerializer = GameRequestSerializer(GameRequestSerializerBase)

private object GameRequestSerializerBase : JsonContentPolymorphicSerializer<IRequestGame>(IRequestGame::class) {
    private const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IRequestGame> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return GameRequestStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IRequest::class} implementation"
            )
    }
}

class GameRequestSerializer<T : IRequestGame>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        GameRequestStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IRequest instance in RequestSerializer"
            )
}
