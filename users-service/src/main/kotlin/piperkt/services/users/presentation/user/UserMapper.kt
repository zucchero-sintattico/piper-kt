package piperkt.services.users.presentation.user

import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.Username
import piperkt.services.users.infrastructure.persistence.model.UserEntity

object UserMapper {
    fun User.toDTO() = UserDTO(username.value, description, profilePicture)

    fun UserDTO.toDomain() = User(Username(username), "", description, profilePicture)

    fun UserEntity.toDomain() =
        User(Username(username), password, description, profilePicture, refreshToken)

    fun User.toEntity() =
        UserEntity(null, username.value, password, description, profilePicture, refreshToken)
}
