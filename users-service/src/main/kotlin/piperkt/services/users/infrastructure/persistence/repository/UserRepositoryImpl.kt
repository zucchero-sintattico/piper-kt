package piperkt.services.users.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username
import piperkt.services.users.infrastructure.persistence.model.UserEntityMapper.toDomain
import piperkt.services.users.infrastructure.persistence.model.UserEntityMapper.toEntity
import piperkt.services.users.infrastructure.persistence.model.UserEntityRepository

@Singleton
class UserRepositoryImpl(private val userEntityRepository: UserEntityRepository) : UserRepository {
    override fun findAll(): List<User> {
        return userEntityRepository.findAll().map { it.toDomain() }
    }

    override fun findByUsername(username: String): User? {
        return userEntityRepository.findByUsername(username)?.toDomain()
    }

    override fun findByRefreshToken(refreshToken: String): User? {
        return userEntityRepository.findByRefreshToken(refreshToken)?.toDomain()
    }

    override fun findById(id: Username): User? {
        return userEntityRepository.findByUsername(id.value)?.toDomain()
    }

    override fun save(entity: User) {
        userEntityRepository.save(entity.toEntity())
    }

    override fun deleteById(id: Username): User? {
        val user = findById(id)
        userEntityRepository.deleteByUsername(id.value)
        return user
    }

    override fun update(entity: User) {
        val user = userEntityRepository.findByUsername(entity.username.value)
        userEntityRepository.updateByUsername(user!!.username, entity.toEntity(user.id))
    }

    override fun deleteAll() {
        userEntityRepository.deleteAll()
    }
}
