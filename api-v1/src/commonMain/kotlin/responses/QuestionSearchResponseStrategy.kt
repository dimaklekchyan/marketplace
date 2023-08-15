package responses

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.IRequest
import ru.klekchyan.marketplace.api.v1.models.IResponse
import ru.klekchyan.marketplace.api.v1.models.QuestionSearchRequest
import ru.klekchyan.marketplace.api.v1.models.QuestionSearchResponse
import kotlin.reflect.KClass

object QuestionSearchResponseStrategy: IResponseStrategy {
    override val discriminator: String = "searchQuestion"
    override val clazz: KClass<out IResponse> = QuestionSearchResponse::class
    override val serializer: KSerializer<out IResponse> = QuestionSearchResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is QuestionSearchResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}