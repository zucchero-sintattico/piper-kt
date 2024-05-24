package piperkt.services.users.infrastructure.persistence.model

import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.Username

/** Mapper for user entities. */
object UserEntityMapper {
    /** Convert a user entity to a user. */
    fun UserEntity.toDomain() =
        User(
            username = Username(username),
            password = password,
            email = email,
            description = description,
            profilePicture = profilePicture,
            refreshToken = refreshToken
        )

    /** Convert a user to a user entity. */
    fun User.toEntity(actualId: String? = null) =
        UserEntity(
            id = actualId,
            username = username.value,
            password = password,
            email = email,
            description = description,
            profilePicture = profilePicture,
            refreshToken = refreshToken
        )
}
