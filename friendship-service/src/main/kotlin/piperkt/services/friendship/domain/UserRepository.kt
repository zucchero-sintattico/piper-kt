package piperkt.services.friendship.domain

interface UserRepository {
    fun findByEmail(email: String): User?

    fun save(user: User): User
}
