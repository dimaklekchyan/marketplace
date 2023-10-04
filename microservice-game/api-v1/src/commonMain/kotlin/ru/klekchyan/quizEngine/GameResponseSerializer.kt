package ru.klekchyan.quizEngine

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.klekchyan.quizEngine.api.v1.models.IResponse
import ru.klekchyan.quizEngine.api.v1.models.IResponseGame
import ru.klekchyan.quizEngine.strategy.GameResponseStrategy


val gameResponseSerializer = GameResponseSerializer(GameResponseSerializerBase)

private object GameResponseSerializerBase : JsonContentPolymorphicSerializer<IResponseGame>(IResponseGame::class) {
    private const val discriminator = "responseType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IResponseGame> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return GameResponseStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IResponse::class} implementation"
            )
    }
}

class GameResponseSerializer<T : IResponseGame>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        GameResponseStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IResponse instance in ResponseSerializer"
            )
}
