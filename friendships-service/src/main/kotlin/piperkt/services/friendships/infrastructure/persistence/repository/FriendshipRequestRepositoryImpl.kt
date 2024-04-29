package piperkt.services.friendships.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import piperkt.common.id.FriendshipRequestId
import piperkt.services.friendships.application.FriendshipRequestRepository
import piperkt.services.friendships.domain.FriendshipRequest
import piperkt.services.friendships.infrastructure.persistence.model.FriendshipRequestEntity
import piperkt.services.friendships.infrastructure.persistence.model.FriendshipRequestModelRepository
import piperkt.services.friendships.infrastructure.persistence.model.toDomain

@Singleton
@Primary
class FriendshipRequestRepositoryImpl(
    private val friendshipRequestModelRepository: FriendshipRequestModelRepository
) : FriendshipRequestRepository {
    override fun findByUser(user: String): List<FriendshipRequest> {
        return friendshipRequestModelRepository.findByToUser(user).map { it.toDomain() }
    }

    override fun findByMembers(from: String, to: String): FriendshipRequest? {
        return friendshipRequestModelRepository.findByFromUserAndToUser(from, to)?.toDomain()
    }

    override fun deleteAll() {
        friendshipRequestModelRepository.deleteAll()
    }

    override fun findById(id: FriendshipRequestId): FriendshipRequest? {
        return friendshipRequestModelRepository
            .findById(id.value)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun save(entity: FriendshipRequest) {
        friendshipRequestModelRepository.save(FriendshipRequestEntity.fromDomain(entity))
    }

    override fun deleteById(id: FriendshipRequestId): FriendshipRequest? {
        val friendshipRequest = findById(id)
        friendshipRequestModelRepository.deleteById(id.value)
        return friendshipRequest
    }
}
