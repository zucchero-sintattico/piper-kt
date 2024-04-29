package piperkt.services.servers.application.exceptions

sealed class ServerServiceException : Exception() {

    data class UserNotInServerExceptionException(
        override val message: String = "User not in server"
    ) : ServerServiceException()

    data class ServerNotFoundExceptionException(override val message: String = "Server not found") :
        ServerServiceException()

    data class ChannelNotFoundException(override val message: String = "Channel not found") :
        ServerServiceException()

    data class UserNotHasPermissionsException(
        override val message: String = "User not has permissions to perform this action"
    ) : ServerServiceException()
}
