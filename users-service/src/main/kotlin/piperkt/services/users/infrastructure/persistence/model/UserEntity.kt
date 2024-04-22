package piperkt.services.users.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.Username

@MappedEntity
data class UserEntity(
    @Id val username: String,
    val password: String,
    val description: String,
    val profilePicture: ByteArray,
    val refreshToken: String = ""
) {
    fun toDomain(): User {
        return User(
            username = Username(username),
            password = password,
            description = description,
            profilePicture = profilePicture,
            refreshToken = refreshToken
        )
    }

    companion object {
        fun fromDomain(user: User): UserEntity {
            return UserEntity(
                username = user.username.value,
                password = user.password,
                description = user.description,
                profilePicture = user.profilePicture,
                refreshToken = user.refreshToken
            )
        }
    }
}

@Repository
interface UserEntityRepository : CrudRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?

    fun findByRefreshToken(refreshToken: String): UserEntity?
}
