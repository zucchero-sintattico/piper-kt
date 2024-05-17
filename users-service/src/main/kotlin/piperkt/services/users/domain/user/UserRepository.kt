package piperkt.services.users.domain.user

import piperkt.common.ddd.Repository

/** User repository. */
interface UserRepository : Repository<Username, User> {

    /**
     * Find a user by username.
     *
     * @param username The username of the user.
     * @return The user if found, null otherwise.
     */
    fun findByUsername(username: String): User?

    /**
     * Find a user by refresh token.
     *
     * @param refreshToken The refresh token.
     * @return The user if found, null otherwise.
     */
    fun findByRefreshToken(refreshToken: String): User?
}
