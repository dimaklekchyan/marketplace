package requests

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.IRequest
import ru.klekchyan.marketplace.api.v1.models.QuestionReadRequest
import kotlin.reflect.KClass

object QuestionReadRequestStrategy: IRequestStrategy {
    override val discriminator: String = "readQuestion"
    override val clazz: KClass<out IRequest> = QuestionReadRequest::class
    override val serializer: KSerializer<out IRequest> = QuestionReadRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is QuestionReadRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}