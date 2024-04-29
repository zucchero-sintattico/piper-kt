package piperkt.services.friendships.application

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery
import piperkt.services.friendships.domain.factory.MessageFactory

class FriendshipServiceFeatureTest : BasicFriendshipServiceTest() {

    @Test
    fun `should allow to send friend request`() {
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(
                receiver = request.to,
                requestFrom = request.from
            )
        ) shouldBe Result.success(Unit)
        verify(mockedFriendshipRequestRepository).save(any())
        verify(mockedEventPublisher).publish(any())
    }

    @Test
    fun `should allow to accept friend request`() {
        whenever(mockedFriendshipRequestRepository.findByMembers(request.to, request.from))
            .thenReturn(request)
        service
            .acceptFriendshipRequest(
                FriendshipCommand.AcceptFriendshipRequest.Request(
                    sender = request.from,
                    requestFrom = request.to
                )
            )
            .isSuccess shouldBe true
        verify(mockedFriendshipRepository).save(any())
        verify(mockedFriendshipRequestRepository).deleteById(any())
        verify(mockedEventPublisher).publish(any())
    }

    @Test
    fun `should allow to decline friend request`() {
        whenever(mockedFriendshipRequestRepository.findByMembers(request.from, request.to))
            .thenReturn(request)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(
                sender = request.to,
                requestFrom = request.from
            )
        ) shouldBe Result.success(Unit)
        verify(mockedFriendshipRequestRepository).deleteById(any())
    }

    @Test
    fun `should allow to send message`() {
        whenever(mockedFriendshipRepository.findByMembers(request.from, request.to))
            .thenReturn(friendship)
        service.sendMessage(
            FriendshipCommand.SendMessage.Request(
                requestFrom = request.from,
                receiver = request.to,
                content = "Hello"
            )
        ) shouldBe Result.success(Unit)
        verify(mockedFriendshipRepository).save(any())
        verify(mockedEventPublisher).publish(any())
    }

    @Test
    fun `should allow to get messages from a friendship`() {
        val fakeMessages =
            listOf(
                MessageFactory.createMessage(request.from, "Hello"),
                MessageFactory.createMessage(request.to, "Hi")
            )
        friendship.messages.addAll(fakeMessages)
        whenever(mockedFriendshipRepository.findByMembers(any(), any())).thenReturn(friendship)
        service.getMessages(
            FriendshipQuery.GetMessages.Request(
                0,
                10,
                friendship.users.first(),
                friendship.users.last()
            )
        ) shouldBe Result.success(FriendshipQuery.GetMessages.Response(fakeMessages))
    }

    @Test
    fun `should allow to get friendship requests`() {
        val friendshipRequests = listOf(request)
        whenever(mockedFriendshipRequestRepository.findByReceiver(request.to))
            .thenReturn(friendshipRequests)
        service.getFriendshipRequests(
            FriendshipQuery.GetFriendshipRequests.Request(requestFrom = request.to)
        ) shouldBe
                Result.success(
                    FriendshipQuery.GetFriendshipRequests.Response(friendshipRequests.map { it.from })
                )
    }

    @Test
    fun `should allow to get friendships`() {
        val friendships = listOf(friendship)
        whenever(mockedFriendshipRepository.findByUser(request.to)).thenReturn(friendships)
        service.getFriendships(
            FriendshipQuery.GetFriendships.Request(requestFrom = request.to)
        ) shouldBe
                Result.success(
                    FriendshipQuery.GetFriendships.Response(
                        friendships =
                        friendships.map {
                            if (it.users.first() == request.to) {
                                it.users.last()
                            } else {
                                it.users.first()
                            }
                        }
                    )
                )
    }
}
