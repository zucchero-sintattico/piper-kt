package piperkt.services.users.domain.user

/** User factory. */
object UserFactory {

    /**
     * Create a new user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param description The description of the user.
     * @param profilePicture The profile picture of the user.
     * @return The created user.
     */
    fun create(
        username: Username,
        password: String,
        description: String? = null,
        profilePicture: String? = null
    ): User {
        return User(username, password, description, profilePicture)
    }

    /**
     * Copy a user.
     *
     * @param from The user to copy.
     * @param change The changes to apply.
     * @return The copied user.
     */
    fun copy(from: User, change: User.() -> Unit = {}): User {
        return create(from.username, from.password, from.description, from.profilePicture)
            .apply(change)
    }
}
