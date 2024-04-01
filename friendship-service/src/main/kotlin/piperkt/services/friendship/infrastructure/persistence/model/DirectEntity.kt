package piperkt.services.friendship.infrastructure.persistence.model
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import java.time.Instant

@MappedEntity
data class DirectEntity(
    @Id val participants: MutableList<String>,
    val messages: MutableList<MessageEntity>
)

class MessageEntity {
    lateinit var sender: String
    lateinit var content: String
    var timestamp: String = Instant.now().toString()
}


@MongoRepository interface DirectModelRepository : CrudRepository<DirectEntity, String>
