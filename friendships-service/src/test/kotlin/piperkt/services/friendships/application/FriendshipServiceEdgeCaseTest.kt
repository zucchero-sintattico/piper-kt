package piperkt.services.friendships.application

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.whenever
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.exceptions.FriendshipServiceException
import piperkt.services.friendships.domain.FriendshipRequestStatus
import piperkt.services.friendships.domain.factory.FriendshipAggregateFactory
import piperkt.services.friendships.domain.factory.FriendshipRequestFactory

class FriendshipServiceEdgeCaseTest : BasicFriendshipServiceTest() {

    @Test
    fun `should not allow to send friend request if user is not the sender`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(request.from, request.to, "notPeppe")
        ) shouldBe Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
    }

    @Test
    fun `should not allow to send friend request if request already exists`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(
                FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
            )
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(request.from, request.to, request.from)
        ) shouldBe
            Result.failure(FriendshipServiceException.FriendshipRequestAlreadyExistsException())
    }

    @Test
    fun `should not allow to accept friend request if user is not the receiver`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(request.from, request.to, "notCiro")
        ) shouldBe Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
    }

    @Test
    fun `should not allow to accept friend request if request does not exist`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(null)
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
    }

    @Test
    fun `should not allow to accept friend request if request is already accepted`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        friendshipAggregate.friendshipRequest.status = FriendshipRequestStatus.ACCEPTED
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe
            Result.failure(FriendshipServiceException.FriendshipRequestAlreadyExistsException())
    }

    @Test
    fun `should not allow to accept friend request if request is already rejected`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        friendshipAggregate.friendshipRequest.status = FriendshipRequestStatus.REJECTED
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe
            Result.failure(FriendshipServiceException.FriendshipRequestAlreadyExistsException())
    }

    @Test
    fun `should not allow to decline friend request if user is not the receiver`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(request.from, request.to, "notCiro")
        ) shouldBe Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
    }

    @Test
    fun `should not allow to decline friend request if request does not exist`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(null)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
    }

    @Test
    fun `should not allow to decline friend request if request is already accepted`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        friendshipAggregate.friendshipRequest.status = FriendshipRequestStatus.ACCEPTED
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe
            Result.failure(FriendshipServiceException.FriendshipRequestAlreadyExistsException())
    }

    @Test
    fun `should not allow to decline friend request if request is already rejected`() {
        val request = FriendshipRequestFactory.createFriendshipRequest("peppe", "ciro")
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        friendshipAggregate.friendshipRequest.status = FriendshipRequestStatus.REJECTED
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe
            Result.failure(FriendshipServiceException.FriendshipRequestAlreadyExistsException())
    }
}
