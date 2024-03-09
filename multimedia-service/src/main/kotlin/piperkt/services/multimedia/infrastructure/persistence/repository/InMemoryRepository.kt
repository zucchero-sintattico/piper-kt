package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Named
import piperkt.services.multimedia.application.UserRepository
import piperkt.services.multimedia.domain.User

@Named("inMemory")
class InMemoryRepository : UserRepository {
    val users = mutableListOf<User>()

    override fun findByEmail(email: String): User? = users.find { it.email == email }

    override fun save(user: User): User {
        users.add(user)
        return user
    }
}
