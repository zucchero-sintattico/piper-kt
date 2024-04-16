package piperkt.services.users.domain.friends

import piperkt.common.ValueObject
import piperkt.services.users.domain.user.Username

data class FriendRequest(val from: Username, val to: Username, val status: FriendRequestStatus) :
    ValueObject
