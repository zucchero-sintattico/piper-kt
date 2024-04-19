package piperkt.services.friendships.infrastructure.persistence.repository

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.friendships.domain.factory.FriendshipAggregateFactory

@MicronautTest
class FriendshipAggregateRepositoryImplTest(
    private val friendshipAggregateRepository: FriendshipAggregateRepositoryImpl
) : AnnotationSpec() {

    @Test
    fun `should find by user friendship requests`() {
        val friendshipAggregate = FriendshipAggregateFactory.createFriendshipAggregate(
            firstUser = "peppe",
            secondUser = "ciro",
        )
        println(friendshipAggregate.id)
//        friendshipAggregateRepository.save(friendshipAggregate)
//        val entities =
//            friendshipAggregateRepository.findByUserFriendshipRequests(friendshipAggregate.friendshipRequest.from)
//        entities.size shouldBe 1
//        entities[0].let {
//            it.id.value shouldBe friendshipAggregate.id.value
//            it.friendshipRequest.from shouldBe friendshipAggregate.friendshipRequest.from
//            it.friendshipRequest.to shouldBe friendshipAggregate.friendshipRequest.to
//            it.friendshipRequest.status shouldBe friendshipAggregate.friendshipRequest.status
//            it.friendship shouldBe null
//        }
    }

}