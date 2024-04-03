package piperkt.services.multimedia.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.domain.session.SessionEvent
import piperkt.services.multimedia.domain.session.SessionEventPublisher

@KafkaClient
interface ServerEventKafkaPublisher : SessionEventPublisher {

    override fun publish(event: SessionEvent) {
        when (event) {
            is SessionEvent.SessionCreated -> publishSessionCreated(event)
            is SessionEvent.SessionDeleted -> publishSessionDeleted(event)
            is SessionEvent.AllowedUserAdded -> publishAllowedUserAdded(event)
            is SessionEvent.AllowedUserRemoved -> publishAllowedUserRemoved(event)
            is SessionEvent.ParticipantJoined -> publishParticipantJoined(event)
            is SessionEvent.ParticipantLeft -> publishParticipantLeft(event)
        }
    }

    @Topic("session-created") fun publishSessionCreated(event: SessionEvent.SessionCreated)

    @Topic("session-deleted") fun publishSessionDeleted(event: SessionEvent.SessionDeleted)

    @Topic("allowed-user-added") fun publishAllowedUserAdded(event: SessionEvent.AllowedUserAdded)

    @Topic("allowed-user-removed")
    fun publishAllowedUserRemoved(event: SessionEvent.AllowedUserRemoved)

    @Topic("participant-joined") fun publishParticipantJoined(event: SessionEvent.ParticipantJoined)

    @Topic("participant-left") fun publishParticipantLeft(event: SessionEvent.ParticipantLeft)
}
