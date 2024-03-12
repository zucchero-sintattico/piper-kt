package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import org.bson.types.ObjectId

@MappedEntity data class ChannelEntity(@Id val id: ObjectId? = null)

@MongoRepository interface ChannelEntityRepository : CrudRepository<ChannelEntity, ObjectId>
