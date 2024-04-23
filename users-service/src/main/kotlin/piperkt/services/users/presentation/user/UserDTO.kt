package piperkt.services.users.presentation.user

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class UserDTO(val username: String, val description: String, val profilePicture: ByteArray)
