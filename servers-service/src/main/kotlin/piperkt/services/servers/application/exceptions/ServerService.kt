package piperkt.services.servers.application.exceptions

sealed class ServerService : Exception() {

    data class UserNotInServerException(override val message: String = "User not in server") :
        ServerService()

    data class ServerNotFoundException(override val message: String = "Server not found") :
        ServerService()

    data class ServerOrChannelNotFoundException(
        override val message: String = "Server or Channel not found"
    ) : ServerService()

    data class UserNotHasPermissionsException(
        override val message: String = "User not has permissions to perform this action"
    ) : ServerService()
}
