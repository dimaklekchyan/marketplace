package ru.klekchyan.quizEngine

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.klekchyan.quizEngine.api.v1.models.*

@OptIn(ExperimentalSerializationApi::class)
val apiV1Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is GameCreateRequest ->  RequestSerializer(GameCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is GameReadRequest ->  RequestSerializer(GameReadRequest.serializer()) as SerializationStrategy<IRequest>
                is GameUpdateRequest ->  RequestSerializer(GameUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is GameDeleteRequest ->  RequestSerializer(GameDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is GameReadAllRequest ->  RequestSerializer(GameReadAllRequest.serializer()) as SerializationStrategy<IRequest>

                is RoundCreateRequest ->  RequestSerializer(RoundCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is RoundReadRequest ->  RequestSerializer(RoundReadRequest.serializer()) as SerializationStrategy<IRequest>
                is RoundUpdateRequest ->  RequestSerializer(RoundUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is RoundDeleteRequest ->  RequestSerializer(RoundDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is RoundReadAllRequest ->  RequestSerializer(RoundReadAllRequest.serializer()) as SerializationStrategy<IRequest>

                is ThemeCreateRequest ->  RequestSerializer(ThemeCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is ThemeReadRequest ->  RequestSerializer(ThemeReadRequest.serializer()) as SerializationStrategy<IRequest>
                is ThemeUpdateRequest ->  RequestSerializer(ThemeUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is ThemeDeleteRequest ->  RequestSerializer(ThemeDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is ThemeReadAllRequest ->  RequestSerializer(ThemeReadAllRequest.serializer()) as SerializationStrategy<IRequest>

                is QuestionCreateRequest ->  RequestSerializer(QuestionCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionReadRequest ->  RequestSerializer(QuestionReadRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionUpdateRequest ->  RequestSerializer(QuestionUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionDeleteRequest ->  RequestSerializer(QuestionDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionReadAllRequest ->  RequestSerializer(QuestionReadAllRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefault(IRequest::class) {
            requestSerializer
        }
        polymorphicDefaultSerializer(IResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is GameCreateResponse ->  ResponseSerializer(GameCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is GameReadResponse   ->  ResponseSerializer(GameReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is GameUpdateResponse ->  ResponseSerializer(GameUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is GameDeleteResponse ->  ResponseSerializer(GameDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is GameReadAllResponse ->  ResponseSerializer(GameReadAllResponse.serializer()) as SerializationStrategy<IResponse>

                is RoundCreateResponse ->  ResponseSerializer(RoundCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is RoundReadResponse   ->  ResponseSerializer(RoundReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is RoundUpdateResponse ->  ResponseSerializer(RoundUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is RoundDeleteResponse ->  ResponseSerializer(RoundDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is RoundReadAllResponse ->  ResponseSerializer(RoundReadAllResponse.serializer()) as SerializationStrategy<IResponse>

                is ThemeCreateResponse ->  ResponseSerializer(ThemeCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is ThemeReadResponse   ->  ResponseSerializer(ThemeReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is ThemeUpdateResponse ->  ResponseSerializer(ThemeUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is ThemeDeleteResponse ->  ResponseSerializer(ThemeDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is ThemeReadAllResponse ->  ResponseSerializer(ThemeReadAllResponse.serializer()) as SerializationStrategy<IResponse>

                is QuestionCreateResponse ->  ResponseSerializer(QuestionCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is QuestionReadResponse   ->  ResponseSerializer(QuestionReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is QuestionUpdateResponse ->  ResponseSerializer(QuestionUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is QuestionDeleteResponse ->  ResponseSerializer(QuestionDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is QuestionReadAllResponse ->  ResponseSerializer(QuestionReadAllResponse.serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }
        polymorphicDefault(IResponse::class) {
            responseSerializer
        }

        contextual(requestSerializer)
        contextual(responseSerializer)
    }
}

fun Json.encodeResponse(response: IResponse): String = encodeToString(responseSerializer, response)

fun apiV1ResponseSerialize(response: IResponse): String = apiV1Mapper.encodeToString(responseSerializer, response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV1ResponseDeserialize(json: String): T = apiV1Mapper.decodeFromString(responseSerializer, json) as T

fun apiV1RequestSerialize(request: IRequest): String = apiV1Mapper.encodeToString(requestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV1RequestDeserialize(json: String): T = apiV1Mapper.decodeFromString(requestSerializer, json) as T
