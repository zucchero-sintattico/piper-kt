package micronaut.playground.infrastructure.persistence.repository

import jakarta.inject.Named
import micronaut.playground.domain.User
import micronaut.playground.domain.UserRepository

@Named("inMemory")
class InMemoryRepository : UserRepository {

    val users = mutableListOf<User>()

    override fun findByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    override fun save(user: User): User {
        users.add(user)
        return user
    }
}
