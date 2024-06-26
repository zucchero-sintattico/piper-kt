package piperkt.services.users.infrastructure.persistence.model

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.GenericRepository

@MongoRepository
interface UserEntityRepository : GenericRepository<UserEntity, String> {

    fun findAll(): List<UserEntity>

    fun findByUsername(username: String): UserEntity?

    fun findByRefreshToken(refreshToken: String): UserEntity?

    fun deleteByUsername(username: String)

    fun save(entity: UserEntity)

    fun updateByUsername(username: String, entity: UserEntity)

    fun deleteAll()
}
