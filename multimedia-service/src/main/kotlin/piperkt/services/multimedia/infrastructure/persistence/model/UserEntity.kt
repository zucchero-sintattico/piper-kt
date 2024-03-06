package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import jakarta.validation.constraints.NotBlank

@MappedEntity
data class UserEntity(
    @Id val email: String,
    @NotBlank val password: String,
)

@MongoRepository interface UserModelRepository : CrudRepository<UserEntity, String>
