package requests

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.IRequest
import ru.klekchyan.marketplace.api.v1.models.QuestionCreateRequest
import kotlin.reflect.KClass

object QuestionCreateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "createQuestion"
    override val clazz: KClass<out IRequest> = QuestionCreateRequest::class
    override val serializer: KSerializer<out IRequest> = QuestionCreateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is QuestionCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
