package ru.klekchyan.quizEngine

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.klekchyan.quizEngine.api.v1.models.*

@OptIn(ExperimentalSerializationApi::class)
val roundApiV1Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequestRound::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is RoundCreateRequest ->  RoundRequestSerializer(RoundCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is RoundReadRequest ->  RoundRequestSerializer(RoundReadRequest.serializer()) as SerializationStrategy<IRequest>
                is RoundUpdateRequest ->  RoundRequestSerializer(RoundUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is RoundDeleteRequest ->  RoundRequestSerializer(RoundDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is RoundReadAllRequest ->  RoundRequestSerializer(RoundReadAllRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefault(IRequest::class) {
            roundRequestSerializer
        }
        polymorphicDefaultSerializer(IResponseRound::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is RoundCreateResponse ->  RoundResponseSerializer(RoundCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is RoundReadResponse   ->  RoundResponseSerializer(RoundReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is RoundUpdateResponse ->  RoundResponseSerializer(RoundUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is RoundDeleteResponse ->  RoundResponseSerializer(RoundDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is RoundReadAllResponse ->  RoundResponseSerializer(RoundReadAllResponse.serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }
        polymorphicDefault(IResponseRound::class) {
            roundResponseSerializer
        }

        contextual(roundRequestSerializer)
        contextual(roundResponseSerializer)
    }
}

fun Json.encodeRoundResponse(response: IResponseRound): String = encodeToString(roundResponseSerializer, response)

fun roundApiV1ResponseSerialize(response: IResponseRound): String = roundApiV1Mapper.encodeToString(roundResponseSerializer, response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> roundApiV1ResponseDeserialize(json: String): T = roundApiV1Mapper.decodeFromString(roundResponseSerializer, json) as T

fun roundApiV1RequestSerialize(request: IRequestRound): String = roundApiV1Mapper.encodeToString(roundRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> roundApiV1RequestDeserialize(json: String): T = roundApiV1Mapper.decodeFromString(roundRequestSerializer, json) as T
