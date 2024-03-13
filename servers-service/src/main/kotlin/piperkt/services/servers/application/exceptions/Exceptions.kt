package piperkt.services.servers.application.exceptions

data class UserNotInServerException(override val message: String = "User not in server") :
    Exception()

data class ServerOrChannelNotFoundException(
    override val message: String = "Server or Channel not found"
) : Exception()
