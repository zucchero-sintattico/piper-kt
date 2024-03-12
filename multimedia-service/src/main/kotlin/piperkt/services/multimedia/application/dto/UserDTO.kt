package piperkt.services.multimedia.application.dto

import piperkt.services.multimedia.domain.users.User

data class UserDTO(
    val username: String,
) {
    companion object {
        fun fromUser(user: User): UserDTO {
            return UserDTO(user.username.value)
        }
    }
}
