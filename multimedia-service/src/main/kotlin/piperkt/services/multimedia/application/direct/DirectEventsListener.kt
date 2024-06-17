package piperkt.services.multimedia.application.direct

import piperkt.common.events.EventListener
import piperkt.events.FriendshipRequestAcceptedEvent
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.application.session.SessionService.Command.CreateSession
import piperkt.services.multimedia.domain.direct.Direct
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.user.Username

/**
 * Listens for events related to directs messages and updates the directs message repository
 * accordingly.
 */
open class DirectEventsListener(
    private val directRepository: DirectRepository,
    private val sessionService: SessionService,
) : EventListener<FriendshipRequestAcceptedEvent> {
    override fun handle(event: FriendshipRequestAcceptedEvent) {
        val session =
            sessionService.createSession(
                CreateSession(setOf(Username(event.fromUser), Username(event.toUser)))
            )
        val direct =
            Direct(
                id = DirectId(),
                users = setOf(Username(event.fromUser), Username(event.toUser)),
                session.id
            )
        directRepository.save(direct)
    }
}
