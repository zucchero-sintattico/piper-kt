package piperkt.services.servers.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
class ChannelEntity {
    @Id @GeneratedValue val id: String? = null
}
