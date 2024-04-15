package piperkt.services.multimedia.application.direct

import piperkt.common.EventListener
import piperkt.services.multimedia.application.direct.DirectService.Command.CreateDirect
import piperkt.services.multimedia.application.services.SessionService
import piperkt.services.multimedia.application.services.SessionService.Command.CreateSession
import piperkt.services.multimedia.domain.direct.DirectEvent
import piperkt.services.multimedia.domain.user.Username

open class DirectEventsListener(
    private val directService: DirectService,
    private val sessionService: SessionService,
) : EventListener<DirectEvent> {
    override fun handle(event: DirectEvent) {
        when (event) {
            is DirectEvent.FriendRequestAccepted -> onFriendRequestAccepted(event)
        }
    }

    private fun onFriendRequestAccepted(event: DirectEvent.FriendRequestAccepted) {
        val session =
            sessionService.createSession(
                CreateSession(setOf(Username(event.from), Username(event.to)))
            )
        directService.createDirect(
            CreateDirect(
                users = setOf(Username(event.from), Username(event.to)),
                sessionId = session.id
            )
        )
    }
}
