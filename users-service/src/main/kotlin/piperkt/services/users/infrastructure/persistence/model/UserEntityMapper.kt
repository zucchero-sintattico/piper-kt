package piperkt.services.users.infrastructure.persistence.model

import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.Username

/** Mapper for user entities. */
object UserEntityMapper {
    /** Convert a user entity to a user. */
    fun UserEntity.toDomain() =
        User(Username(username), password, description, profilePicture, refreshToken)

    /** Convert a user to a user entity. */
    fun User.toEntity() =
        UserEntity(id = null, username.value, password, description, profilePicture, refreshToken)
}
