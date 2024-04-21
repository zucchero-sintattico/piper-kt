package piperkt.services.friendships.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import piperkt.common.id.FriendshipId
import piperkt.services.friendships.application.FriendshipRepository
import piperkt.services.friendships.domain.Friendship
import piperkt.services.friendships.infrastructure.persistence.model.FriendshipEntity
import piperkt.services.friendships.infrastructure.persistence.model.FriendshipModelRepository
import piperkt.services.friendships.infrastructure.persistence.model.toDomain

@Singleton
@Primary
class FriendshipRepositoryImpl(private val friendshipModelRepository: FriendshipModelRepository) :
    FriendshipRepository {
    override fun findByFriendship(from: String, to: String): Friendship? {
        return friendshipModelRepository.findByUsersContainsAndUsersContains(from, to)?.toDomain()
    }

    override fun findByUserFriendships(user: String): List<Friendship> {
        return friendshipModelRepository.findByUsersContains(user).map { it.toDomain() }
    }

    override fun findById(id: FriendshipId): Friendship? {
        return friendshipModelRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    }

    override fun save(entity: Friendship) {
        friendshipModelRepository.save(FriendshipEntity.fromDomain(entity))
    }

    override fun deleteById(id: FriendshipId): Friendship? {
        val friendship = findById(id)
        friendshipModelRepository.deleteById(id.value)
        return friendship
    }
}
