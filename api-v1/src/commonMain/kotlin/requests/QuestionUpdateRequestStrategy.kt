package requests

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.IRequest
import ru.klekchyan.marketplace.api.v1.models.QuestionUpdateRequest
import kotlin.reflect.KClass

object QuestionUpdateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "updateQuestion"
    override val clazz: KClass<out IRequest> = QuestionUpdateRequest::class
    override val serializer: KSerializer<out IRequest> = QuestionUpdateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is QuestionUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}