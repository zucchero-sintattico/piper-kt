package piperkt.services.servers.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import java.time.Instant

@MappedEntity
data class MessageEntity(
    @Id @GeneratedValue val id: String? = null,
    val content: String,
    val sender: String,
    val timestamp: String = Instant.now().toString()
)

@MongoRepository interface MessageModelRepository : CrudRepository<MessageEntity, String>
