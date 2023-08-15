package requests

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.IRequest
import ru.klekchyan.marketplace.api.v1.models.QuestionSearchRequest
import kotlin.reflect.KClass

object QuestionSearchRequestStrategy: IRequestStrategy {
    override val discriminator: String = "searchQuestion"
    override val clazz: KClass<out IRequest> = QuestionSearchRequest::class
    override val serializer: KSerializer<out IRequest> = QuestionSearchRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is QuestionSearchRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}