package piperkt.services.friendships.application

import piperkt.common.events.FriendshipEvent
import piperkt.common.events.FriendshipEventPublisher
import piperkt.services.friendships.application.api.FriendshipsApi
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery
import piperkt.services.friendships.application.exceptions.FriendshipServiceException
import piperkt.services.friendships.domain.factory.FriendshipRequestFactory
import piperkt.services.friendships.domain.factory.MessageFactory
import piperkt.services.friendships.domain.toFriendship

open class FriendshipService(
    private val friendshipRequestRepository: FriendshipRequestRepository,
    private val friendshipRepository: FriendshipRepository,
    private val eventPublisher: FriendshipEventPublisher
) : FriendshipsApi {

    override fun sendFriendshipRequest(
        request: FriendshipCommand.SendFriendshipRequest.Request
    ): Result<Unit> {
        // Check if they are already friends
        friendshipRepository.findByMembers(request.requestFrom, request.receiver)?.let {
            return Result.failure(FriendshipServiceException.FriendshipAlreadyExistsException())
        }
        // Check if the friendship request already exists
        friendshipRequestRepository.findByReceiver(request.receiver).forEach {
            if (it.from == request.requestFrom) {
                return Result.failure(
                    FriendshipServiceException.FriendshipRequestAlreadyExistsException()
                )
            }
        }
        FriendshipRequestFactory.createFriendshipRequest(request.requestFrom, request.receiver)
            .let { friendshipRequestRepository.save(it) }
        eventPublisher.publish(
            FriendshipEvent.FriendshipRequestSentEvent(request.requestFrom, request.receiver)
        )
        return Result.success(Unit)
    }

    override fun acceptFriendshipRequest(
        request: FriendshipCommand.AcceptFriendshipRequest.Request
    ): Result<FriendshipCommand.AcceptFriendshipRequest.Response> {
        // Check if the friendship request exists
        val friendshipRequest =
            friendshipRequestRepository.findByMembers(
                from = request.sender,
                to = request.requestFrom
            )
                ?: return Result.failure(
                    FriendshipServiceException.FriendshipRequestNotFoundException()
                )

        val friendship = friendshipRequest.toFriendship()
        friendshipRepository.save(friendship)
        friendshipRequestRepository.deleteById(friendshipRequest.id)
        eventPublisher.publish(
            FriendshipEvent.FriendshipRequestAcceptedEvent(request.requestFrom, request.sender)
        )
        return Result.success(
            FriendshipCommand.AcceptFriendshipRequest.Response(friendship.id.value)
        )
    }

    override fun declineFriendshipRequest(
        request: FriendshipCommand.DeclineFriendshipRequest.Request
    ): Result<Unit> {
        // Check if the friendship request exists
        val friendshipRequest =
            friendshipRequestRepository.findByMembers(
                from = request.sender,
                to = request.requestFrom
            )
                ?: return Result.failure(
                    FriendshipServiceException.FriendshipRequestNotFoundException()
                )
        friendshipRequestRepository.deleteById(friendshipRequest.id)
        eventPublisher.publish(
            FriendshipEvent.FriendshipRequestRejectedEvent(request.requestFrom, request.sender)
        )
        return Result.success(Unit)
    }

    override fun sendMessage(
        request: FriendshipCommand.SendMessage.Request
    ): Result<FriendshipCommand.SendMessage.Response> {
        // Check if the friendship exists
        val friendship =
            friendshipRepository.findByMembers(request.requestFrom, request.receiver)
                ?: return Result.failure(FriendshipServiceException.FriendshipNotFoundException())
        val message =
            MessageFactory.createMessage(sender = request.requestFrom, content = request.content)
        friendship.addMessage(message)
        friendshipRepository.update(friendship)
        eventPublisher.publish(
            FriendshipEvent.NewMessageInFriendshipEvent(
                request.requestFrom,
                request.receiver,
                message.id
            )
        )
        return Result.success(FriendshipCommand.SendMessage.Response(message.id.value))
    }

    override fun getMessages(
        request: FriendshipQuery.GetMessages.Request
    ): Result<FriendshipQuery.GetMessages.Response> {
        val friendship =
            friendshipRepository.findByMembers(request.requestFrom, request.friend)
                ?: return Result.failure(FriendshipServiceException.FriendshipNotFoundException())
        return Result.success(FriendshipQuery.GetMessages.Response(friendship.messages))
    }

    override fun getFriendshipRequests(
        request: FriendshipQuery.GetFriendshipRequests.Request
    ): Result<FriendshipQuery.GetFriendshipRequests.Response> {
        val friendshipRequests =
            friendshipRequestRepository.findByReceiver(request.requestFrom).map {
                if (it.from == request.requestFrom) {
                    it.to
                } else {
                    it.from
                }
            }
        return Result.success(FriendshipQuery.GetFriendshipRequests.Response(friendshipRequests))
    }

    override fun getFriendships(
        request: FriendshipQuery.GetFriendships.Request
    ): Result<FriendshipQuery.GetFriendships.Response> {
        val friendships =
            friendshipRepository.findByUser(request.requestFrom).map {
                if (it.users.first() == request.requestFrom) {
                    it.users.last()
                } else {
                    it.users.first()
                }
            }
        return Result.success(FriendshipQuery.GetFriendships.Response(friendships))
    }
}
