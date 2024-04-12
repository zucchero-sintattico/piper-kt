package piperkt.services.multimedia.domain.direct

import piperkt.common.DomainEvent

sealed interface DirectEvent : DomainEvent {
    data class FriendRequestAccepted(val from: String, val to: String) : DirectEvent
}
