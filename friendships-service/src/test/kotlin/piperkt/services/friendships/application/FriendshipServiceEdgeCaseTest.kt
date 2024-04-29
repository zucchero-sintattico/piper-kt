package piperkt.services.friendships.application

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.exceptions.FriendshipServiceException

class FriendshipServiceEdgeCaseTest : BasicFriendshipServiceTest() {

    @Test
    fun `should not allow to send friend request if request already exists`() {
        whenever(mockedFriendshipRequestRepository.findByReceiver(request.from))
            .thenReturn(listOf(request))
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(
                receiver = request.to,
                requestFrom = request.from
            )
        ) shouldBe
                Result.failure(FriendshipServiceException.FriendshipRequestAlreadyExistsException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to accept friend request if request does not exist`() {
        whenever(mockedFriendshipRequestRepository.findByMembers(request.to, request.from))
            .thenReturn(null)
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(
                sender = request.to,
                requestFrom = request.to
            )
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to accept friend request if request is already accepted`() {
        whenever(mockedFriendshipRepository.findByMembers(request.to, request.from))
            .thenReturn(friendship)
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(
                sender = request.to,
                requestFrom = request.from
            )
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
        verifyNoInteractions(mockedEventPublisher)
    }

    @Test
    fun `should not allow to decline friend request if request does not exist`() {
        whenever(mockedFriendshipRequestRepository.findByMembers(request.from, request.to))
            .thenReturn(null)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(
                sender = request.to,
                requestFrom = request.from
            )
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
    }

    @Test
    fun `should not allow to decline friend request if request is already accepted`() {
        whenever(mockedFriendshipRepository.findByMembers(any(), any())).thenReturn(friendship)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(
                sender = request.from,
                requestFrom = request.to
            )
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipAlreadyExistsException())
    }

    @Test
    fun `should not allow to send message if friendship does not exist`() {
        whenever(mockedFriendshipRepository.findByMembers(request.from, request.to))
            .thenReturn(null)
        service.sendMessage(
            FriendshipCommand.SendMessage.Request(
                requestFrom = request.from,
                receiver = request.to,
                content = "Hello"
            )
        ) shouldBe Result.failure(FriendshipServiceException.FriendshipNotFoundException())
        verifyNoInteractions(mockedEventPublisher)
    }
}
