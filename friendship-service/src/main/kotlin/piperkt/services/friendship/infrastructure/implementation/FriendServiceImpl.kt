package piperkt.services.friendship.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.friendship.application.FriendService
import piperkt.services.friendship.domain.FriendRepository

@Singleton
class FriendServiceImpl(
    userRepository: FriendRepository,
) : FriendService(userRepository)
