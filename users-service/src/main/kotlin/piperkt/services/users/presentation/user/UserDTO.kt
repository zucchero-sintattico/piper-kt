package piperkt.services.users.presentation.user

import io.micronaut.serde.annotation.Serdeable

/**
 * Data transfer object for the user.
 *
 * @param username The username of the user.
 * @param description The description of the user.
 * @param profilePicture The profile picture of the user.
 */
@Serdeable
data class UserDTO(
    val username: String,
    val email: String? = null,
    val description: String? = null,
    val profilePicture: String? = null,
)
