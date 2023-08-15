package responses

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.IRequest
import ru.klekchyan.marketplace.api.v1.models.IResponse
import ru.klekchyan.marketplace.api.v1.models.QuestionUpdateRequest
import ru.klekchyan.marketplace.api.v1.models.QuestionUpdateResponse
import kotlin.reflect.KClass

object QuestionUpdateResponseStrategy: IResponseStrategy {
    override val discriminator: String = "updateQuestion"
    override val clazz: KClass<out IResponse> = QuestionUpdateResponse::class
    override val serializer: KSerializer<out IResponse> = QuestionUpdateResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is QuestionUpdateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}