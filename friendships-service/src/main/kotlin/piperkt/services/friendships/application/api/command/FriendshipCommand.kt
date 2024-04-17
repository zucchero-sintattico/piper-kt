package piperkt.services.friendships.application.api.command

import piperkt.services.friendships.domain.Message

sealed interface FriendshipCommand {
    sealed interface CreateFriendship : FriendshipCommand {
        data class Request(val firstUser: String, val secondUser: String) : CreateFriendship
    }

    sealed interface SendFriendshipRequest : FriendshipCommand {
        data class Request(val sender: String, val receiver: String) : SendFriendshipRequest
    }

    sealed interface AcceptFriendshipRequest : FriendshipCommand {
        data class Request(val sender: String, val receiver: String) : AcceptFriendshipRequest
    }

    sealed interface DeclineFriendshipRequest : FriendshipCommand {
        data class Request(val sender: String, val receiver: String) : DeclineFriendshipRequest
    }

    sealed interface SendMessage : FriendshipCommand {
        data class Request(val sender: String, val receiver: String, val content: String) :
            SendMessage
    }

    sealed interface GetMessages : FriendshipCommand {
        data class Request(val sender: String, val receiver: String) : GetMessages

        data class Response(val messages: List<Message>) : GetMessages
    }

    sealed interface GetFriendshipRequests : FriendshipCommand {
        data class Request(val receiver: String) : GetFriendshipRequests

        data class Response(val requests: List<String>) : GetFriendshipRequests
    }

    sealed interface GetFriendships : FriendshipCommand {
        data class Request(val user: String) : GetFriendships

        data class Response(val friendships: List<String>) : GetFriendships
    }
}
