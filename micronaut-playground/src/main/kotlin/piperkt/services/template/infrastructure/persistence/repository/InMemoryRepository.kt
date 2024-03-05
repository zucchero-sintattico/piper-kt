package piperkt.services.template.infrastructure.persistence.repository

import jakarta.inject.Named
import piperkt.services.template.domain.User
import piperkt.services.template.domain.UserRepository

@Named("inMemory")
class InMemoryRepository : UserRepository {
    val users = mutableListOf<User>()

    override fun findByEmail(email: String): User? = users.find { it.email == email }

    override fun save(user: User): User {
        users.add(user)
        return user
    }
}
