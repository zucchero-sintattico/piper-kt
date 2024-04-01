package piperkt.services.friendship.application

import piperkt.services.friendship.application.api.DirectServiceApi
import piperkt.services.friendship.domain.DirectRepository
import piperkt.services.friendship.domain.Message

class DirectService(private val directRepository: DirectRepository): DirectServiceApi {
    override fun getDirectMessagesPaginated(username: String, friendUsername: String, from: Int, limit: Int) =
        directRepository.getDirectMessagesPaginated(username, friendUsername, from, limit)

    override fun createDirect(username: String, friendUsername: String) {
        directRepository.createDirect(username, friendUsername)
    }

    override fun sendDirectMessage(username: String, friendUsername: String, message: Message) {
        directRepository.sendDirectMessage(username, friendUsername, message)
    }
}