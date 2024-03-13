package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MappedEntity
data class DirectEntity(@Id val id: String? = null, var users: Set<String>, var sessionId: String)

@MongoRepository
interface DirectEntityRepository : CrudRepository<DirectEntity, String> {
    fun findByUsers(users: Set<String>): DirectEntity?
}
