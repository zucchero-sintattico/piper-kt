package piperkt.services.servers.domain

interface UserRepository {
    fun findByEmail(email: String): User?

    fun save(user: User): User
}
