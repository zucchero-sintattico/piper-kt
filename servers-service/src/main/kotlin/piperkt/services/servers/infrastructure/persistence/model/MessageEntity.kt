package piperkt.services.servers.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class MessageEntity(@Id @GeneratedValue val id: String? = null, val content: String)
