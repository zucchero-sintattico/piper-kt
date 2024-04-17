package piperkt.services.friendships.application

import piperkt.services.friendships.application.api.FriendshipsApi
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery
import piperkt.services.friendships.application.exceptions.FriendshipServiceException
import piperkt.services.friendships.domain.factory.FriendshipAggregateFactory

class FriendshipService(
    private val repository: FriendshipAggregateRepository,
) : FriendshipsApi {
    override fun createFriendship(
        request: FriendshipCommand.CreateFriendship.Request
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun sendFriendshipRequest(
        request: FriendshipCommand.SendFriendshipRequest.Request
    ): Result<Unit> {
        if (request.requestFrom != request.sender) {
            return Result.failure(FriendshipServiceException.UserNotHasPermissionsException())
        }
        repository.findFriendshipRequest(request.sender, request.receiver)?.let {
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
        TODO("Not yet implemented")
    }

    override fun declineFriendshipRequest(
        request: FriendshipCommand.DeclineFriendshipRequest.Request
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun sendMessage(request: FriendshipCommand.SendMessage.Request): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getMessages(
        request: FriendshipQuery.GetMessages.Request
    ): Result<FriendshipQuery.GetMessages.Response> {
        TODO("Not yet implemented")
    }

    override fun getFriendshipRequests(
        request: FriendshipQuery.GetFriendshipRequests.Request
    ): Result<FriendshipQuery.GetFriendshipRequests.Response> {
        TODO("Not yet implemented")
    }

    override fun getFriendships(
        request: FriendshipQuery.GetFriendships.Request
    ): Result<FriendshipQuery.GetFriendships.Response> {
        TODO("Not yet implemented")
    }
}
