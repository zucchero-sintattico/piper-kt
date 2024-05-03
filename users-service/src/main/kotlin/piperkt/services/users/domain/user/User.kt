package piperkt.services.users.domain.user

import piperkt.common.AggregateRoot
import piperkt.common.EntityId

/**
 * Username entity.
 *
 * @param value The value of the username.
 */
data class Username(override val value: String) : EntityId<String>(value)

/**
 * User aggregate root.
 *
 * @param username The username of the user.
 * @param password The password of the user.
 * @param description The description of the user.
 * @param profilePicture The profile picture of the user.
 * @param refreshToken The refresh token of the user.
 */
class User(
    val username: Username,
    var password: String,
    var description: String? = null,
    var profilePicture: String? = null,
    var refreshToken: String? = null
) : AggregateRoot<Username>(username) {

    /**
     * Update the description of the user.
     *
     * @param description The new description.
     */
    fun updateDescription(description: String?) {
        this.description = description
    }

    /**
     * Update the profile picture of the user.
     *
     * @param profilePicture The new profile picture.
     */
    fun updateProfilePicture(profilePicture: String?) {
        this.profilePicture = profilePicture
    }

    /**
     * Update the password of the user.
     *
     * @param password The new password.
     */
    fun updateRefreshToken(refreshToken: String?) {
        this.refreshToken = refreshToken
    }

    /** Clear the refresh token of the user. */
    fun clearRefreshToken() = updateRefreshToken(null)
}
