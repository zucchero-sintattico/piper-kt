package piperkt.services.users.presentation.user

import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.Username

/** User mapper to convert between domain and DTO objects. */
object UserMapper {
    /** Convert a user to a user DTO. */
    fun User.toDTO() = UserDTO(username.value, description, profilePicture)

    /** Convert a user DTO to a user. */
    fun UserDTO.toDomain() = User(Username(username), "", description, profilePicture)
}
