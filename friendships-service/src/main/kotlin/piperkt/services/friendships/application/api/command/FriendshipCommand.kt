package piperkt.services.friendships.application.api.command

import piperkt.services.friendships.application.api.ServiceRequest

sealed interface FriendshipCommand {
    sealed interface CreateFriendship : FriendshipCommand {
        data class Request(
            val firstUser: String,
            val secondUser: String,
            override val requestFrom: String
        ) : CreateFriendship, ServiceRequest
    }

    sealed interface SendFriendshipRequest : FriendshipCommand {
        data class Request(
            val sender: String,
            val receiver: String,
            override val requestFrom: String
        ) : SendFriendshipRequest, ServiceRequest
    }

    sealed interface AcceptFriendshipRequest : FriendshipCommand {
        data class Request(
            val sender: String,
            val receiver: String,
            override val requestFrom: String
        ) : AcceptFriendshipRequest, ServiceRequest
    }

    sealed interface DeclineFriendshipRequest : FriendshipCommand {
        data class Request(
            val sender: String,
            val receiver: String,
            override val requestFrom: String
        ) : DeclineFriendshipRequest, ServiceRequest
    }

    sealed interface SendMessage : FriendshipCommand {
        data class Request(
            val sender: String,
            val receiver: String,
            val content: String,
            override val requestFrom: String
        ) : SendMessage, ServiceRequest
    }
}
