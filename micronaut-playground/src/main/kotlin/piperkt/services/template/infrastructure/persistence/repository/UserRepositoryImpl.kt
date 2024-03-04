package piperkt.services.template.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import piperkt.services.template.domain.User
import piperkt.services.template.domain.UserRepository
import piperkt.services.template.infrastructure.persistence.model.UserEntity
import piperkt.services.template.infrastructure.persistence.model.UserModelRepository

@Singleton
@Primary
class UserRepositoryImpl(private val userModelRepository: UserModelRepository) : UserRepository {
    override fun findByEmail(email: String): User? {
        return userModelRepository.findById(email).map { User(it.email, it.password) }.orElse(null)
    }

    override fun save(user: User): User {
        val userModel = UserEntity(user.email, user.password)
        return userModelRepository.save(userModel).let { User(it.email, it.password) }
    }
}
