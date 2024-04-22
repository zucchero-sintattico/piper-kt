package mocks.repositories

import piperkt.common.InMemoryRepository
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username

class InMemoryUserRepository(entities: Map<Username, User> = mapOf()) :
    InMemoryRepository<Username, User>(entities), UserRepository {
    override fun findByUsername(username: String): User? {
        return super.entities[Username(username)]
    }

    override fun findByRefreshToken(refreshToken: String): User? {
        return super.entities.values.find { it.refreshToken == refreshToken }
    }
}
