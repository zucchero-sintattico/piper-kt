package piperkt.services.users.application

import piperkt.common.orThrow
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserEvent.UserUpdated
import piperkt.services.users.domain.user.UserEventPublisher
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username

open class UserService(
    private val userRepository: UserRepository,
    private val userEventPublisher: UserEventPublisher
) {

    fun getUser(username: String): User {
        val user =
            userRepository
                .findByUsername(username)
                .orThrow(UserError.UserNotFound(Username(username)))
        return user
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

    fun updateUserDescription(username: String, description: String): User {
        updateUser(Username(username)) { updateDescription(description) }
        userEventPublisher.publish(UserUpdated(username, description = description))
        return getUser(username)
    }

    fun updateUserProfilePicture(username: String, profilePicture: ByteArray): User {
        updateUser(Username(username)) { updateProfilePicture(profilePicture) }
        userEventPublisher.publish(UserUpdated(username, profilePicture = profilePicture))
        return getUser(username)
    }
}
