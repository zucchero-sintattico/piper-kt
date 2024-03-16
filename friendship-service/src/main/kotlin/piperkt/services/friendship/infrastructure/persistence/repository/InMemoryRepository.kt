package piperkt.services.friendship.infrastructure.persistence.repository

import jakarta.inject.Named
import piperkt.services.friendship.domain.User
import piperkt.services.friendship.domain.UserRepository

@Named("inMemory")
class InMemoryRepository : UserRepository {
    val users = mutableListOf<User>()

    override fun findByEmail(email: String): User? = users.find { it.email == email }

    override fun save(user: User): User {
        users.add(user)
        return user
    }
}
