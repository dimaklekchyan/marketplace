package requests

import IApiStrategy
import ru.klekchyan.marketplace.api.v1.models.IRequest

sealed interface IRequestStrategy: IApiStrategy<IRequest> {
    companion object {
        private val members: List<IRequestStrategy> = listOf(
            QuestionCreateRequestStrategy,
            QuestionReadRequestStrategy,
            QuestionUpdateRequestStrategy,
            QuestionDeleteRequestStrategy,
            QuestionSearchRequestStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
