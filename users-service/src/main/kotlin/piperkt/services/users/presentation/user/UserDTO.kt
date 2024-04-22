package piperkt.services.users.presentation.user

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.users.domain.user.User

@Serdeable
data class UserDTO(val username: String, val description: String, val profilePicture: ByteArray)

fun User.toDTO() = UserDTO(username.value, description, profilePicture)
