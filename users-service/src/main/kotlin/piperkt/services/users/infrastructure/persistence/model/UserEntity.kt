package piperkt.services.users.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.GenericRepository

@MappedEntity
data class UserEntity(
    @Id @GeneratedValue val id: String? = null,
    val username: String,
    val password: String,
    val description: String,
    val profilePicture: ByteArray,
    val refreshToken: String? = null
)

@MongoRepository
interface UserEntityRepository : GenericRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?

    fun findByRefreshToken(refreshToken: String): UserEntity?

    fun deleteByUsername(username: String)

    fun save(entity: UserEntity)
}
