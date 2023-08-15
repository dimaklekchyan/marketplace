package requests

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.IRequest
import ru.klekchyan.marketplace.api.v1.models.QuestionDeleteRequest
import kotlin.reflect.KClass

object QuestionDeleteRequestStrategy: IRequestStrategy {
    override val discriminator: String = "deleteQuestion"
    override val clazz: KClass<out IRequest> = QuestionDeleteRequest::class
    override val serializer: KSerializer<out IRequest> = QuestionDeleteRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is QuestionDeleteRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}