package responses

import IApiStrategy
import ru.klekchyan.marketplace.api.v1.models.IResponse

sealed interface IResponseStrategy: IApiStrategy<IResponse> {
    companion object {
        val members: List<IResponseStrategy> = listOf(
            QuestionCreateResponseStrategy,
            QuestionReadResponseStrategy,
            QuestionUpdateResponseStrategy,
            QuestionDeleteResponseStrategy,
            QuestionSearchResponseStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
