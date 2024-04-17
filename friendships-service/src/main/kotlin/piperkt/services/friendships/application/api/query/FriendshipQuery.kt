package piperkt.services.friendships.application.api.query

import piperkt.services.friendships.application.api.ServiceRequest
import piperkt.services.friendships.domain.Message

sealed interface FriendshipQuery {
    sealed interface GetMessages : FriendshipQuery {
        data class Request(
            val sender: String,
            val receiver: String,
            override val requestFrom: String
        ) : GetMessages, ServiceRequest

        data class Response(val messages: List<Message>) : GetMessages
    }

    sealed interface GetFriendshipRequests : FriendshipQuery {
        data class Request(val receiver: String, override val requestFrom: String) :
            GetFriendshipRequests, ServiceRequest

        data class Response(val requests: List<String>) : GetFriendshipRequests
    }

    sealed interface GetFriendships : FriendshipQuery {
        data class Request(val user: String, override val requestFrom: String) :
            GetFriendships, ServiceRequest

        data class Response(val friendships: List<String>) : GetFriendships
    }
}
