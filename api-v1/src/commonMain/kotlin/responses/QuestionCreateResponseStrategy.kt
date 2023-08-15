package responses

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.IResponse
import ru.klekchyan.marketplace.api.v1.models.QuestionCreateRequest
import ru.klekchyan.marketplace.api.v1.models.QuestionCreateResponse
import kotlin.reflect.KClass

object QuestionCreateResponseStrategy: IResponseStrategy {
    override val discriminator: String = "createQuestion"
    override val clazz: KClass<out IResponse> = QuestionCreateResponse::class
    override val serializer: KSerializer<out IResponse> = QuestionCreateResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is QuestionCreateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
