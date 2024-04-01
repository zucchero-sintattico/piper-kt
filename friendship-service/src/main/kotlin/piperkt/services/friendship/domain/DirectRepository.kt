package piperkt.services.friendship.domain

interface DirectRepository {

    fun getDirectMessagesPaginated(username: String, friendUsername: String, from: Int, limit: Int): List<Message>

    fun createDirect(username: String, friendUsername: String)

    fun sendDirectMessage(username: String, friendUsername: String, message: Message)
}