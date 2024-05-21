package piperkt.services.multimedia.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.events.*

@KafkaClient
interface SessionEventKafkaPublisher : SessionEventPublisher {

    override fun publish(event: SessionEvent) {
        when (event) {
            is SessionCreated -> publishSessionCreated(event)
            is SessionDeleted -> publishSessionDeleted(event)
            is AllowedUserAdded -> publishAllowedUserAdded(event)
            is AllowedUserRemoved -> publishAllowedUserRemoved(event)
            is ParticipantJoined -> publishParticipantJoined(event)
            is ParticipantLeft -> publishParticipantLeft(event)
        }
    }

    @Topic("session-created") fun publishSessionCreated(event: SessionCreated)

    @Topic("session-deleted") fun publishSessionDeleted(event: SessionDeleted)

    @Topic("allowed-user-added") fun publishAllowedUserAdded(event: AllowedUserAdded)

    @Topic("allowed-user-removed") fun publishAllowedUserRemoved(event: AllowedUserRemoved)

    @Topic("participant-joined") fun publishParticipantJoined(event: ParticipantJoined)

    @Topic("participant-left") fun publishParticipantLeft(event: ParticipantLeft)
}
