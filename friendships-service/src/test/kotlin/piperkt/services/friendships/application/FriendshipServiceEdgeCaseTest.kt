package piperkt.services.friendships.application

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery
import piperkt.services.friendships.application.exceptions.FriendshipServiceException

class FriendshipServiceEdgeCaseTest : BasicFriendshipServiceTest() {

    @Test
    fun `should not allow to send friend request if user is not the sender`() {
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(request.from, request.to, "notPeppe")
        ) shouldBe Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to send friend request if request already exists`() {
        whenever(mockedFriendshipRequestRepository.findByUserFriendshipRequests(request.from))
            .thenReturn(listOf(request))
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(request.from, request.to, request.from)
        ) shouldBe
            Result.failure(FriendshipServiceException.FriendshipRequestAlreadyExistsException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to accept friend request if user is not the receiver`() {
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(request.from, request.to, "notCiro")
        ) shouldBe Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to accept friend request if request does not exist`() {
        whenever(
                mockedFriendshipRequestRepository.findByFriendshipRequest(request.from, request.to)
            )
            .thenReturn(null)
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to accept friend request if request is already accepted`() {
        whenever(mockedFriendshipRepository.findByFriendship(request.from, request.to))
            .thenReturn(friendship)
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to decline friend request if user is not the receiver`() {
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(request.from, request.to, "notCiro")
        ) shouldBe Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
    }

    @Test
    fun `should not allow to decline friend request if request does not exist`() {
        whenever(
                mockedFriendshipRequestRepository.findByFriendshipRequest(request.from, request.to)
            )
            .thenReturn(null)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
    }

    @Test
    fun `should not allow to decline friend request if request is already accepted`() {
        whenever(mockedFriendshipRepository.findByFriendship(request.from, request.to))
            .thenReturn(friendship)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipAlreadyExistsException())
    }

    @Test
    fun `should not allow to send message if friendship does not exist`() {
        whenever(mockedFriendshipRepository.findByFriendship(request.from, request.to))
            .thenReturn(null)
        service.sendMessage(
            FriendshipCommand.SendMessage.Request(request.from, request.to, "Hello", request.from)
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipNotFoundException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to send message if sender is not in the friendship`() {
        whenever(mockedFriendshipRepository.findByFriendship(request.from, request.to))
            .thenReturn(friendship)
        service.sendMessage(
            FriendshipCommand.SendMessage.Request(request.from, request.to, "Hello", "notPeppe")
        ) shouldBe Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to send message if receiver is not in the friendship`() {
        whenever(mockedFriendshipRepository.findByFriendship(request.from, request.to))
            .thenReturn(null)
        service.sendMessage(
            FriendshipCommand.SendMessage.Request(request.from, request.to, "Hello", request.from)
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipNotFoundException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to get messages from a friendship if user is not in the friendship`() {
        whenever(mockedFriendshipRepository.findByFriendship(request.from, request.to))
            .thenReturn(null)
        service.getMessages(
            FriendshipQuery.GetMessages.Request(0, 10, request.to, "notPeppe")
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipNotFoundException())
    }
}
