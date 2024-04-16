package piperkt.services.users.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.Username

@MappedEntity
data class UserEntity(
    @Id val username: String,
    val password: String,
    val description: String,
    val profilePicture: ByteArray,
    val friends: List<String>,
    val friendRequests: List<FriendRequestEntity>,
    val sentFriendRequests: List<FriendRequestEntity>
) {
    fun toDomain(): User {
        return User(
            username = Username(username),
            password = password,
            description = description,
            profilePicture = profilePicture,
            friends = friends.map { Username(it) },
            friendRequests = friendRequests.map { it.toDomain() },
            sentFriendRequests = sentFriendRequests.map { it.toDomain() }
        )
    }

    companion object {
        fun fromDomain(user: User): UserEntity {
            return UserEntity(
                username = user.username.value,
                password = user.password,
                description = user.description,
                profilePicture = user.profilePicture,
                friends = user.friends.map { it.value },
                friendRequests = user.friendRequests.map { FriendRequestEntity.fromDomain(it) },
                sentFriendRequests =
                    user.sentFriendRequests.map { FriendRequestEntity.fromDomain(it) }
            )
        }
    }
}

@Repository
interface UserEntityRepository : CrudRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
}
