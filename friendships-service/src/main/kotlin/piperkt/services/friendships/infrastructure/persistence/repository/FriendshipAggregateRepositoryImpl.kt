package piperkt.services.friendships.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import piperkt.common.id.FriendshipAggregateId
import piperkt.services.friendships.application.FriendshipAggregateRepository
import piperkt.services.friendships.domain.FriendshipAggregate
import piperkt.services.friendships.infrastructure.persistence.model.FriendshipAggregateEntity
import piperkt.services.friendships.infrastructure.persistence.model.FriendshipAggregateModelRepository
import piperkt.services.friendships.infrastructure.persistence.model.toDomain

@Singleton
@Primary
class FriendshipAggregateRepositoryImpl(private val friendshipAggregateModelRepository: FriendshipAggregateModelRepository) :
    FriendshipAggregateRepository {
    override fun findByUserFriendshipRequests(user: String): List<FriendshipAggregate> {
        return friendshipAggregateModelRepository.
        }
    }

    override fun findByFriendshipRequest(from: String, to: String): FriendshipAggregate? {
        return friendshipAggregateModelRepository.findByFriendshipRequestFromUserAndFriendshipRequestToUser(from, to)
            ?.toDomain()
    }

    override fun findByFriendship(from: String, to: String): FriendshipAggregate? {
        return friendshipAggregateModelRepository.findByFriendshipRequestFromUserAndFriendshipRequestToUser(from, to)
            ?.toDomain()

    }

    override fun findByUserFriendships(user: String): List<FriendshipAggregate> {
        return friendshipAggregateModelRepository.findByFriendship_UsersContains(user).map {
            it.toDomain()
        }
    }

    override fun findAll(): List<FriendshipAggregate> {
        return friendshipAggregateModelRepository.findAll().map {
            it.toDomain()
        }
    }

    override fun findById(id: FriendshipAggregateId): FriendshipAggregate? {
        return friendshipAggregateModelRepository.findById(id.value).map {
            it.toDomain()
        }.orElse(null)
    }

    override fun save(entity: FriendshipAggregate) {
        friendshipAggregateModelRepository.save(
            FriendshipAggregateEntity.fromDomain(entity)
        )
    }

    override fun deleteById(id: FriendshipAggregateId): FriendshipAggregate? {
        return friendshipAggregateModelRepository.findById(id.value).map {
            it.toDomain()
        }.orElse(null).also {
            friendshipAggregateModelRepository.deleteById(id.value)
        }
    }

}