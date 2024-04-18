package piperkt.services.friendships.application

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.whenever
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.domain.FriendshipRequestStatus
import piperkt.services.friendships.domain.factory.FriendshipAggregateFactory
import piperkt.services.friendships.domain.factory.FriendshipRequestFactory

class FriendshipServiceFeatureTest : BasicFriendshipServiceTest() {

    @Test
    fun `should allow to send friend request`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(request.from, request.to, request.from)
        ) shouldBe Result.success(Unit)
    }

    @Test
    fun `should allow to accept friend request`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        friendshipAggregate.friendshipRequest.status = FriendshipRequestStatus.PENDING
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.success(Unit)
    }

    @Test
    fun `should allow to decline friend request`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        friendshipAggregate.friendshipRequest.status = FriendshipRequestStatus.PENDING
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.success(Unit)
    }

    @Test
    fun `should allow to send message`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        whenever(mockedRepository.findByFriendship(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.sendMessage(
            FriendshipCommand.SendMessage.Request(request.from, request.to, "Hello", request.from)
        ) shouldBe Result.success(Unit)
    }
}
