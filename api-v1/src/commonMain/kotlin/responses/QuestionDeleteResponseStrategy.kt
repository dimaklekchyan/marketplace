package responses

import kotlinx.serialization.KSerializer
import ru.klekchyan.marketplace.api.v1.models.*
import kotlin.reflect.KClass

object QuestionDeleteResponseStrategy: IResponseStrategy {
    override val discriminator: String = "deleteQuestion"
    override val clazz: KClass<out IResponse> = QuestionDeleteResponse::class
    override val serializer: KSerializer<out IResponse> = QuestionDeleteResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is QuestionDeleteResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}