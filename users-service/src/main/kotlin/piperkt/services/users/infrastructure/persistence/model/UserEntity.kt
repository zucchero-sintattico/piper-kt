package piperkt.services.users.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

/** User entity. */
@MappedEntity
data class UserEntity(
    @Id @GeneratedValue val id: String? = null,
    val username: String,
    val password: String,
    val description: String? = null,
    val profilePicture: String? = null,
    val refreshToken: String? = null
)
