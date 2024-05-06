package piperkt.services.multimedia.application.direct

import piperkt.common.orThrow
import piperkt.services.multimedia.domain.direct.Direct
import piperkt.services.multimedia.domain.direct.DirectErrors
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username

open class DirectService(private val directRepository: DirectRepository) {

    sealed interface Command {

        data class CreateDirect(val users: Set<Username>, val sessionId: SessionId) : Command

        data class DeleteDirect(val id: DirectId) : Command
    }

    fun getDirect(id: DirectId): Direct {
        return directRepository.findById(id).orThrow(DirectErrors.DirectNotFound(id))
    }

    fun createDirect(command: Command.CreateDirect) {
        val direct = Direct(DirectId(command.users), command.sessionId)
        directRepository.save(direct)
    }

    fun deleteDirect(command: Command.DeleteDirect) {
        directRepository.deleteById(command.id)
    }
}
