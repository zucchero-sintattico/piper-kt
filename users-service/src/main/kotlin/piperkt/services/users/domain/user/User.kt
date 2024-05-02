package piperkt.services.users.domain.user

import piperkt.common.AggregateRoot

class User(
    val username: Username,
    var password: String,
    var description: String = "",
    var profilePicture: ByteArray = ByteArray(0),
    var refreshToken: String? = null
) : AggregateRoot<Username>(username) {

    fun updateDescription(description: String) {
        this.description = description
    }

    fun updateProfilePicture(profilePicture: ByteArray) {
        this.profilePicture = profilePicture
    }

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun clearRefreshToken() {
        this.refreshToken = null
    }

    fun copy(
        username: Username = this.username,
        password: String = this.password,
        description: String = this.description,
        profilePicture: ByteArray = this.profilePicture,
        refreshToken: String? = this.refreshToken
    ): User {
        return User(username, password, description, profilePicture, refreshToken)
    }
}
