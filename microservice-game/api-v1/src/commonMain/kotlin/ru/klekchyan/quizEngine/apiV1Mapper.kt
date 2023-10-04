package ru.klekchyan.quizEngine

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.klekchyan.quizEngine.api.v1.models.*

@OptIn(ExperimentalSerializationApi::class)
val gameApiV1Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequestGame::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is GameCreateRequest ->  GameRequestSerializer(GameCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is GameReadRequest ->  GameRequestSerializer(GameReadRequest.serializer()) as SerializationStrategy<IRequest>
                is GameUpdateRequest ->  GameRequestSerializer(GameUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is GameDeleteRequest ->  GameRequestSerializer(GameDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is GameReadAllRequest ->  GameRequestSerializer(GameReadAllRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefault(IRequest::class) {
            gameRequestSerializer
        }
        polymorphicDefaultSerializer(IResponseGame::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is GameCreateResponse ->  GameResponseSerializer(GameCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is GameReadResponse   ->  GameResponseSerializer(GameReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is GameUpdateResponse ->  GameResponseSerializer(GameUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is GameDeleteResponse ->  GameResponseSerializer(GameDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is GameReadAllResponse ->  GameResponseSerializer(GameReadAllResponse.serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }
        polymorphicDefault(IResponseGame::class) {
            gameResponseSerializer
        }

        contextual(gameRequestSerializer)
        contextual(gameResponseSerializer)
    }
}

fun Json.encodeGameResponse(response: IResponseGame): String = encodeToString(gameResponseSerializer, response)

fun gameApiV1ResponseSerialize(response: IResponseGame): String = gameApiV1Mapper.encodeToString(gameResponseSerializer, response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> gameApiV1ResponseDeserialize(json: String): T = gameApiV1Mapper.decodeFromString(gameResponseSerializer, json) as T

fun gameApiV1RequestSerialize(request: IRequestGame): String = gameApiV1Mapper.encodeToString(gameRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> gameApiV1RequestDeserialize(json: String): T = gameApiV1Mapper.decodeFromString(gameRequestSerializer, json) as T
