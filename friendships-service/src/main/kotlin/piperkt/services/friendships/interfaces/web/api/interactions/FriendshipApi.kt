package piperkt.services.friendships.interfaces.web.api.interactions

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.friendships.interfaces.web.api.dto.MessageDTO

sealed interface FriendshipApi {

    sealed interface SendFriendshipRequest : FriendshipApi {
        @Serdeable data class Request(val receiver: String) : SendFriendshipRequest

        @Serdeable data class Response(val response: String) : SendFriendshipRequest
    }

    sealed interface AcceptFriendshipRequest : FriendshipApi {
        @Serdeable
        data class Request(
            val sender: String,
        ) : AcceptFriendshipRequest

        @Serdeable data class Response(val friendshipId: String) : AcceptFriendshipRequest
    }

    sealed interface DeclineFriendshipRequest : FriendshipApi {
        @Serdeable
        data class Request(
            val sender: String,
        ) : DeclineFriendshipRequest

        @Serdeable data class Response(val response: String) : DeclineFriendshipRequest
    }

    sealed interface SendMessage : FriendshipApi {
        @Serdeable
        data class Request(
            val content: String,
        ) : SendMessage

        @Serdeable data class Response(val messageId: String) : SendMessage
    }

    sealed interface GetFriendshipMessages : FriendshipApi {
        @Serdeable data class Response(val messages: List<MessageDTO>) : GetFriendshipMessages
    }

    sealed interface GetFriendshipRequests : FriendshipApi {
        @Serdeable data class Response(val requests: List<String>) : GetFriendshipRequests
    }

    sealed interface GetFriendships : FriendshipApi {
        @Serdeable data class Response(val friendships: List<String>) : GetFriendships
    }
}
