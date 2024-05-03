package piperkt.services.users.domain.user

sealed class UserError : Exception() {
    /**
     * User not found error.
     *
     * @param username The username of the user.
     */
    data class UserNotFound(val username: Username) : UserError()

    /**
     * Refresh token not found error.
     *
     * @param refreshToken The refresh token.
     */
    data class RefreshTokenNotFound(val refreshToken: String) : UserError()

    /**
     * User already exists error.
     *
     * @param username The username of the user.
     */
    data class UserAlreadyExists(val username: Username) : UserError()

    /**
     * Invalid password error.
     *
     * @param password The password.
     */
    data class InvalidPassword(val password: String) : UserError()
}
