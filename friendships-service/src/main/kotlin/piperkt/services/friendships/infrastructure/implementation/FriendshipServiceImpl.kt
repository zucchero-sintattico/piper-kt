package piperkt.services.friendships.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.common.events.FriendshipEventPublisher
import piperkt.services.friendships.application.FriendshipRepository
import piperkt.services.friendships.application.FriendshipRequestRepository
import piperkt.services.friendships.application.FriendshipService

@Singleton
class FriendshipServiceImpl(
    friendshipRepository: FriendshipRepository,
    friendshipRequestRepository: FriendshipRequestRepository,
    friendshipEventPublisher: FriendshipEventPublisher

) : FriendshipService(
    friendshipRepository = friendshipRepository,
    friendshipRequestRepository = friendshipRequestRepository,
    eventPublisher = friendshipEventPublisher
)