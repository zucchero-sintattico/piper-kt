package piperkt.services.users.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username
import piperkt.services.users.infrastructure.persistence.model.UserEntity
import piperkt.services.users.infrastructure.persistence.model.UserEntityRepository

@Singleton
class UserRepositoryImpl(private val userEntityRepository: UserEntityRepository) : UserRepository {
    override fun findByUsername(username: String): User? {
        return userEntityRepository.findByUsername(username)?.toDomain()
    }

    override fun findByRefreshToken(refreshToken: String): User? {
        return userEntityRepository.findByRefreshToken(refreshToken)?.toDomain()
    }

    override fun findById(id: Username): User? {
        return userEntityRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    }

    override fun save(entity: User) {
        userEntityRepository.save(UserEntity.fromDomain(entity))
    }

    override fun deleteById(id: Username): User? {
        val user = findById(id)
        userEntityRepository.deleteById(id.value)
        return user
    }
}
