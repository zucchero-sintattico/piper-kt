package piperkt.services.servers.application.exceptions

sealed class ServerServiceException : Exception() {

    data class UserNotInServerException(override val message: String = "User not in server") :
        ServerServiceException()

    data class ServerNotFoundException(override val message: String = "Server not found") :
        ServerServiceException()

    data class ServerOrChannelNotFoundException(
        override val message: String = "Server or Channel not found"
    ) : ServerServiceException()

    data class UserNotHasPermissionsException(
        override val message: String = "User not has permissions to perform this action"
    ) : ServerServiceException()
}
