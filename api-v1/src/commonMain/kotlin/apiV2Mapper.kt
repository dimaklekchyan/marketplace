import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.klekchyan.marketplace.api.v1.models.*

@OptIn(ExperimentalSerializationApi::class)
val apiV1Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is QuestionCreateRequest ->  RequestSerializer(QuestionCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionReadRequest ->  RequestSerializer(QuestionReadRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionUpdateRequest ->  RequestSerializer(QuestionUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionDeleteRequest ->  RequestSerializer(QuestionDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is QuestionSearchRequest ->  RequestSerializer(QuestionSearchRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefault(IRequest::class) {
            requestSerializer
        }
        polymorphicDefaultSerializer(IResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is QuestionCreateResponse ->  ResponseSerializer(QuestionCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is QuestionReadResponse   ->  ResponseSerializer(QuestionReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is QuestionUpdateResponse ->  ResponseSerializer(QuestionUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is QuestionDeleteResponse ->  ResponseSerializer(QuestionDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is QuestionSearchResponse ->  ResponseSerializer(QuestionSearchResponse.serializer()) as SerializationStrategy<IResponse>
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
