package piperkt.services.friendships.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.events.FriendshipEventPublisher
import piperkt.services.friendships.application.FriendshipRequestRepository
import piperkt.services.friendships.application.FriendshipService
import piperkt.services.friendships.domain.FriendshipRepository

@Singleton
class FriendshipServiceImpl(
    friendshipRepository: FriendshipRepository,
    friendshipRequestRepository: FriendshipRequestRepository,
    friendshipEventPublisher: FriendshipEventPublisher,
) :
    FriendshipService(
        friendshipRepository = friendshipRepository,
        friendshipRequestRepository = friendshipRequestRepository,
        eventPublisher = friendshipEventPublisher
    )
