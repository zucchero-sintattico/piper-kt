package piperkt.services.friendships.application

import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery
import piperkt.services.friendships.domain.FriendshipRequestStatus
import piperkt.services.friendships.domain.factory.FriendshipAggregateFactory
import piperkt.services.friendships.domain.factory.MessageFactory

class FriendshipServiceFeatureTest : BasicFriendshipServiceTest() {

    @Test
    fun `should allow to send friend request`() {
        service.sendFriendshipRequest(
            FriendshipCommand.SendFriendshipRequest.Request(request.from, request.to, request.from)
        ) shouldBe Result.success(Unit)
        verify(mockedRepository).save(any())
        verify(mockedEventPublisher).publish(any())
    }

    @Test
    fun `should allow to accept friend request`() {
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        friendshipAggregate.friendshipRequest.status = FriendshipRequestStatus.PENDING
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.acceptFriendshipRequest(
            FriendshipCommand.AcceptFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.success(Unit)
        verify(mockedRepository).save(any())
        verify(mockedEventPublisher).publish(any())
    }

    @Test
    fun `should allow to decline friend request`() {
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        friendshipAggregate.friendshipRequest.status = FriendshipRequestStatus.PENDING
        whenever(mockedRepository.findByFriendshipRequest(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.declineFriendshipRequest(
            FriendshipCommand.DeclineFriendshipRequest.Request(request.from, request.to, request.to)
        ) shouldBe Result.success(Unit)
        verify(mockedRepository).save(any())
    }

    @Test
    fun `should allow to send message`() {
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        whenever(mockedRepository.findByFriendship(request.from, request.to))
            .thenReturn(friendshipAggregate)
        service.sendMessage(
            FriendshipCommand.SendMessage.Request(request.from, request.to, "Hello", request.from)
        ) shouldBe Result.success(Unit)
        verify(mockedRepository).save(any())
        verify(mockedEventPublisher).publish(any())
    }

    @Test
    fun `should allow to get messages from a friendship`() {
        val fakeMessages =
            listOf(
                MessageFactory.createMessage(request.from, "Hello"),
                MessageFactory.createMessage(request.to, "Hi")
            )
        val friendshipAggregateWithMessage =
            FriendshipAggregateFactory.createFriendshipAggregate(
                request.from,
                request.to,
                fakeMessages
            )

        whenever(mockedRepository.findByFriendship(any(), any()))
            .thenReturn(friendshipAggregateWithMessage)
        service.getMessages(
            FriendshipQuery.GetMessages.Request(0, 10, request.from, request.to)
        ) shouldBe Result.success(FriendshipQuery.GetMessages.Response(fakeMessages))
    }

    @Test
    fun `should allow to get friendship requests`() {
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        whenever(mockedRepository.findByUserFriendshipRequests(request.to))
            .thenReturn(listOf(friendshipAggregate))
        service.getFriendshipRequests(
            FriendshipQuery.GetFriendshipRequests.Request(request.to)
        ) shouldBe
            Result.success(
                FriendshipQuery.GetFriendshipRequests.Response(
                    listOf(friendshipAggregate.friendshipRequest.from)
                )
            )
    }

    @Test
    fun `should allow to get friendships`() {
        val friendshipAggregate =
            FriendshipAggregateFactory.createFriendshipAggregate(request.from, request.to)
        whenever(mockedRepository.findByUserFriendships(request.to))
            .thenReturn(listOf(friendshipAggregate))
        service.getFriendships(FriendshipQuery.GetFriendships.Request(request.to)) shouldBe
            Result.success(
                FriendshipQuery.GetFriendships.Response(
                    listOf(friendshipAggregate.friendshipRequest.from)
                )
            )
    }
}
