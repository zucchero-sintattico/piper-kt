package piperkt.services.users.application

import piperkt.common.utils.orThrow
import piperkt.events.UserEvent.UserUpdated
import piperkt.events.UserEventPublisher
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username

open class UserService(
    private val userRepository: UserRepository,
    private val userEventPublisher: UserEventPublisher,
) {

    fun getUser(username: String): User {
        return userRepository
            .findByUsername(username)
            .orThrow(UserError.UserNotFound(Username(username)))
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

    fun updateUserDescription(username: String, description: String?): User {
        val updated = updateUser(Username(username)) { updateDescription(description) }
        userEventPublisher.publish(UserUpdated(username, description = description))
        return updated
    }

    fun updateUserProfilePicture(username: String, profilePicture: String?): User {
        val updated = updateUser(Username(username)) { updateProfilePicture(profilePicture) }
        userEventPublisher.publish(UserUpdated(username, profilePicture = profilePicture))
        return updated
    }
}
