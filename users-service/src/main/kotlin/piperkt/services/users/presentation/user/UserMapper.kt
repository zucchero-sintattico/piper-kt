package piperkt.services.users.presentation.user

import piperkt.services.users.domain.user.User

/** User mapper to convert between domain and DTO objects. */
object UserMapper {
    /** Convert a user to a user DTO. */
    fun User.toDTO() = UserDTO(username.value, email, description, profilePicture)
}
