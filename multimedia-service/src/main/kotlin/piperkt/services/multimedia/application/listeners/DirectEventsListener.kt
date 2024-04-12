package piperkt.services.multimedia.application.listeners

import piperkt.common.EventListener
import piperkt.services.multimedia.domain.direct.Direct
import piperkt.services.multimedia.domain.direct.DirectEvent
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.session.SessionEvent
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.Username

open class DirectEventsListener(
    private val directRepository: DirectRepository,
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher,
) : EventListener<DirectEvent> {
    override fun handle(event: DirectEvent) {
        when (event) {
            is DirectEvent.FriendRequestAccepted -> onFriendRequestAccepted(event)
        }
    }

    private fun onFriendRequestAccepted(event: DirectEvent.FriendRequestAccepted) {
        val id = DirectId(setOf(Username(event.from), Username(event.to)))
        val session = SessionFactory.fromAllowedUsers(id.value)
        sessionRepository.save(session)
        val direct = Direct(id, session.id)
        directRepository.save(direct)
        sessionEventPublisher.publish(
            SessionEvent.SessionCreated(session.id, session.allowedUsers())
        )
    }
}
