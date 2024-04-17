package piperkt.services.friendships.application.exceptions

sealed class FriendshipServiceException : Exception() {
    data class FriendshipRequestNotFoundException(
        override val message: String = "Friendship request not found"
    ) : FriendshipServiceException()

    data class FriendshipNotFoundException(override val message: String = "Friendship not found") :
        FriendshipServiceException()

    data class FriendshipRequestAlreadyExistsException(
        override val message: String = "Friendship request already exists"
    ) : FriendshipServiceException()

    data class FriendshipAlreadyExistsException(
        override val message: String = "Friendship already exists"
    ) : FriendshipServiceException()

    data class UserNotHasPermissionsException(
        override val message: String = "User not has permissions to perform this action"
    ) : FriendshipServiceException()
}
