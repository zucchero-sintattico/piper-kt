package piperkt.services.friendship.application.api

import piperkt.services.friendship.domain.Message

interface DirectServiceApi {
    fun getDirectMessagesPaginated(
        username: String,
        friendUsername: String,
        from: Int,
        limit: Int
    ): List<Message>

    fun createDirect(username: String, friendUsername: String)

    fun sendDirectMessage(username: String, friendUsername: String, message: Message)
}
