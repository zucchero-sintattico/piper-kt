package piperkt.services.users.application

import piperkt.common.orThrow
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserEvent.UserUpdated
import piperkt.services.users.domain.user.UserEventPublisher
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username

data class UserDTO(
    val username: String,
    val description: String,
    val profilePicture: ByteArray,
)

fun User.toDTO() = UserDTO(username.value, description, profilePicture)

open class UserService(
    private val userRepository: UserRepository,
    private val userEventPublisher: UserEventPublisher
) {

    fun getUser(username: String): UserDTO {
        val user =
            userRepository
                .findByUsername(username)
                .orThrow(UserError.UserNotFound(Username(username)))
        return user.toDTO()
    }

    private fun updateUser(username: Username, update: User.() -> Unit): User {
        val user =
            userRepository
                .findByUsername(username.value)
                .orThrow(UserError.UserNotFound(Username(username.value)))
        user.update()
        userRepository.save(user)
        return user
    }

    fun updateUserDescription(username: String, description: String) {
        updateUser(Username(username)) { updateDescription(description) }
        userEventPublisher.publish(UserUpdated(Username(username), description = description))
    }

    fun updateUserProfilePicture(username: String, profilePicture: ByteArray) {
        updateUser(Username(username)) { updateProfilePicture(profilePicture) }
        userEventPublisher.publish(UserUpdated(Username(username), profilePicture = profilePicture))
    }
}
