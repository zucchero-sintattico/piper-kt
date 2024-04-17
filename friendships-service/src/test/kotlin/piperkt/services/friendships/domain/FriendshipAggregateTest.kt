package piperkt.services.friendships.domain

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import piperkt.services.friendships.domain.factory.FriendshipAggregateFactory

class FriendshipAggregateTest : AnnotationSpec() {

    private val friendships =
        listOf(
            FriendshipAggregateFactory.createFriendshipAggregate("jane", "john"),
            FriendshipAggregateFactory.createFriendshipAggregate("john", "pete"),
        )

    @Test
    fun `friendships can be filtered by user`() {
        val janeFriendships =
            friendships.filter { friendshipAggregate ->
                friendshipAggregate.friendship?.users?.contains("jane") ?: false
            }
        val johnFriendships =
            friendships.filter { friendshipAggregate ->
                friendshipAggregate.friendship?.users?.contains("john") ?: false
            }
        val peteFriendships =
            friendships.filter { friendshipAggregate ->
                friendshipAggregate.friendship?.users?.contains("pete") ?: false
            }

        janeFriendships.size shouldBe 1
        johnFriendships.size shouldBe 2
        peteFriendships.size shouldBe 1
    }
}
