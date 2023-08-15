package responses

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.IRequest
import ru.klekchyan.marketplace.api.v1.models.IResponse
import ru.klekchyan.marketplace.api.v1.models.QuestionReadRequest
import ru.klekchyan.marketplace.api.v1.models.QuestionReadResponse
import kotlin.reflect.KClass

object QuestionReadResponseStrategy: IResponseStrategy {
    override val discriminator: String = "readQuestion"
    override val clazz: KClass<out IResponse> = QuestionReadResponse::class
    override val serializer: KSerializer<out IResponse> = QuestionReadResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is QuestionReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}