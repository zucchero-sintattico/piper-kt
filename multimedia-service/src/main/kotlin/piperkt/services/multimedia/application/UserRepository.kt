package piperkt.services.multimedia.application

import piperkt.services.multimedia.domain.User

interface UserRepository {
    fun findByEmail(email: String): User?

    fun save(user: User): User
}
