package piperkt.services.friendships.application

import piperkt.services.friendships.application.api.FriendshipsApi
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery
import piperkt.services.friendships.application.exceptions.FriendshipServiceException
import piperkt.services.friendships.domain.FriendshipRequest
import piperkt.services.friendships.domain.FriendshipRequestStatus
import piperkt.services.friendships.domain.factory.FriendshipAggregateFactory
import piperkt.services.friendships.domain.factory.MessageFactory
import piperkt.services.friendships.domain.toFriendship

class FriendshipService(
    private val repository: FriendshipAggregateRepository,
) : FriendshipsApi {

    override fun sendFriendshipRequest(
        request: FriendshipCommand.SendFriendshipRequest.Request
    ): Result<Unit> {
        if (request.requestFrom != request.sender) {
            return Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
        }
        repository.findByFriendshipRequest(request.sender, request.receiver)?.let {
            return Result.failure(
                FriendshipServiceException.FriendshipRequestAlreadyExistsException()
            )
        }
        FriendshipAggregateFactory.createFriendshipAggregate(request.sender, request.receiver).let {
            repository.save(it)
        }
        return Result.success(Unit)
    }

    override fun acceptFriendshipRequest(
        request: FriendshipCommand.AcceptFriendshipRequest.Request
    ): Result<Unit> {
        // Only the receiver can accept the friendship request
        if (request.requestFrom != request.receiver) {
            return Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
        }
        repository.findByFriendshipRequest(request.sender, request.receiver)?.let {
            if (checkIfRequestIsAlreadyAcceptedOrRejected(it.friendshipRequest)) {
                return Result.failure(
                    FriendshipServiceException.FriendshipRequestAlreadyExistsException()
                )
            } else {
                // Create a new friendship between the two users
                it.friendshipRequest.status = FriendshipRequestStatus.ACCEPTED
                repository.save(it.copy(friendship = it.friendshipRequest.toFriendship()))
                return Result.success(Unit)
            }
        }
        return Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
    }

    private fun checkIfRequestIsAlreadyAcceptedOrRejected(request: FriendshipRequest) =
        request.status == FriendshipRequestStatus.ACCEPTED ||
            request.status == FriendshipRequestStatus.REJECTED

    override fun declineFriendshipRequest(
        request: FriendshipCommand.DeclineFriendshipRequest.Request
    ): Result<Unit> {
        // Only the receiver can decline the friendship request
        if (request.requestFrom != request.receiver) {
            return Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
        }
        repository.findByFriendshipRequest(request.sender, request.receiver)?.let {
            if (checkIfRequestIsAlreadyAcceptedOrRejected(it.friendshipRequest)) {
                return Result.failure(
                    FriendshipServiceException.FriendshipRequestAlreadyExistsException()
                )
            } else {
                it.friendshipRequest.status = FriendshipRequestStatus.REJECTED
                repository.save(it)
                return Result.success(Unit)
            }
        }
        return Result.failure(FriendshipServiceException.FriendshipRequestNotFoundException())
    }

    override fun sendMessage(request: FriendshipCommand.SendMessage.Request): Result<Unit> {
        val friendshipAggregate =
            repository.findByFriendship(request.sender, request.receiver)
                ?: return Result.failure(FriendshipServiceException.FriendshipNotFoundException())
        // I'm sure that if repository find the aggregate by the friendship, the friendship is not
        // null
        val friendship = friendshipAggregate.friendship!!
        if (!friendship.users.contains(request.requestFrom)) {
            return Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
        }
        friendshipAggregate.friendship.addMessage(
            MessageFactory.createMessage(sender = request.sender, content = request.receiver)
        )
        repository.save(friendshipAggregate)
        return Result.success(Unit)
    }

    override fun getMessages(
        request: FriendshipQuery.GetMessages.Request
    ): Result<FriendshipQuery.GetMessages.Response> {
        val friendshipAggregate =
            repository.findByFriendship(request.requestFrom, request.friend)
                ?: return Result.failure(FriendshipServiceException.FriendshipNotFoundException())
        val friendship = friendshipAggregate.friendship!!
        return Result.success(
            FriendshipQuery.GetMessages.Response(
                // Take the last messages from the list
                friendship.messages.subList(
                    request.index,
                    request.offset.coerceAtMost(friendship.messages.size)
                )
            )
        )
    }

    override fun getFriendshipRequests(
        request: FriendshipQuery.GetFriendshipRequests.Request
    ): Result<FriendshipQuery.GetFriendshipRequests.Response> {
        val friendshipRequests =
            repository
                .findByUserFriendshipRequests(request.requestFrom)
                .map { it.friendshipRequest.from }
                .toList()
        return Result.success(FriendshipQuery.GetFriendshipRequests.Response(friendshipRequests))
    }

    override fun getFriendships(
        request: FriendshipQuery.GetFriendships.Request
    ): Result<FriendshipQuery.GetFriendships.Response> {
        val friendships =
            repository.findByUserFriendships(request.requestFrom).map {
                if (it.friendshipRequest.from == request.requestFrom) {
                    it.friendshipRequest.to
                } else {
                    it.friendshipRequest.from
                }
            }
        return Result.success(FriendshipQuery.GetFriendships.Response(friendships))
    }
}
