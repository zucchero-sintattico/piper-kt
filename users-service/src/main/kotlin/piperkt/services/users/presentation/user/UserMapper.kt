package piperkt.services.users.presentation.user

import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.Username
import piperkt.services.users.infrastructure.persistence.model.UserEntity

/** User mapper to convert between domain and DTO objects. */
object UserMapper {
    /** Convert a user to a user DTO. */
    fun User.toDTO() = UserDTO(username.value, email, description, profilePicture)

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
