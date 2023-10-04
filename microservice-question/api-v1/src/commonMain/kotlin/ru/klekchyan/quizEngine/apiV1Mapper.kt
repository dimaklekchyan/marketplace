package ru.klekchyan.quizEngine

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.klekchyan.quizEngine.api.v1.models.*

@OptIn(ExperimentalSerializationApi::class)
val questionApiV1Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequestQuestion::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is QuestionCreateRequest ->  QuestionRequestSerializer(QuestionCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionReadRequest ->  QuestionRequestSerializer(QuestionReadRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionUpdateRequest ->  QuestionRequestSerializer(QuestionUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionDeleteRequest ->  QuestionRequestSerializer(QuestionDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionReadAllRequest ->  QuestionRequestSerializer(QuestionReadAllRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefault(IRequest::class) {
            questionRequestSerializer
        }
        polymorphicDefaultSerializer(IResponseQuestion::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is QuestionCreateResponse ->  QuestionResponseSerializer(QuestionCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is QuestionReadResponse   ->  QuestionResponseSerializer(QuestionReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is QuestionUpdateResponse ->  QuestionResponseSerializer(QuestionUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is QuestionDeleteResponse ->  QuestionResponseSerializer(QuestionDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is QuestionReadAllResponse ->  QuestionResponseSerializer(QuestionReadAllResponse.serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }
        polymorphicDefault(IResponseQuestion::class) {
            questionResponseSerializer
        }

        contextual(questionRequestSerializer)
        contextual(questionResponseSerializer)
    }
}

fun Json.encodeQuestionResponse(response: IResponseQuestion): String = encodeToString(questionResponseSerializer, response)

fun questionApiV1ResponseSerialize(response: IResponseQuestion): String = questionApiV1Mapper.encodeToString(questionResponseSerializer, response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> questionApiV1ResponseDeserialize(json: String): T = questionApiV1Mapper.decodeFromString(questionResponseSerializer, json) as T

fun questionApiV1RequestSerialize(request: IRequestQuestion): String = questionApiV1Mapper.encodeToString(questionRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> questionApiV1RequestDeserialize(json: String): T = questionApiV1Mapper.decodeFromString(questionRequestSerializer, json) as T
