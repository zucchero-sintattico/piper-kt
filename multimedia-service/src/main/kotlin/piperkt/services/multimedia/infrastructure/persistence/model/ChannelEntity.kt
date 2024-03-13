package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MappedEntity data class ChannelEntity(@Id val id: String? = null)

@MongoRepository interface ChannelEntityRepository : CrudRepository<ChannelEntity, String>
