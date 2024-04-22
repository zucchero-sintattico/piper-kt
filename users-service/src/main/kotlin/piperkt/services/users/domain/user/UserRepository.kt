package piperkt.services.users.domain.user

import piperkt.common.Repository

interface UserRepository : Repository<Username, User> {
    fun findByUsername(username: String): User?

    fun findByRefreshToken(refreshToken: String): User?
}
