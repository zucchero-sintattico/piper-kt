package piperkt.services.friendships.application.api.query

import piperkt.services.friendships.application.api.ServiceRequest
import piperkt.services.friendships.domain.DirectMessage

sealed interface FriendshipQuery {
    sealed interface GetMessages : FriendshipQuery {
        data class Request(
            val index: Int,
            val offset: Int,
            val friend: String,
            override val requestFrom: String
        ) : GetMessages, ServiceRequest

        data class Response(val directMessages: List<DirectMessage>) : GetMessages
    }

    sealed interface GetFriendshipRequests : FriendshipQuery {
        data class Request(override val requestFrom: String) :
            GetFriendshipRequests, ServiceRequest

        data class Response(val requests: List<String>) : GetFriendshipRequests
    }

    sealed interface GetFriendships : FriendshipQuery {
        data class Request(override val requestFrom: String) : GetFriendships, ServiceRequest

        data class Response(val friendships: List<String>) : GetFriendships
    }
}
