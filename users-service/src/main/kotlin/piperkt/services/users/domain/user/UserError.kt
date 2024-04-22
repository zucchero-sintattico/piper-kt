package piperkt.services.users.domain.user

sealed class UserError : Exception() {
    data class UserNotFound(val username: Username) : UserError()

    data class RefreshTokenNotFound(val refreshToken: String) : UserError()

    data class UserAlreadyExists(val username: Username) : UserError()

    data class InvalidPassword(val password: String) : UserError()
}
