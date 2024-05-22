package piperkt.services.multimedia.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.events.*

@KafkaClient
interface SessionEventKafkaPublisher : SessionEventPublisher {

    override fun publish(event: SessionEvent) {
        when (event) {
            is SessionCreatedEvent -> publishSessionCreated(event)
            is SessionDeletedEvent -> publishSessionDeleted(event)
            is AllowedUserAddedEvent -> publishAllowedUserAdded(event)
            is AllowedUserRemovedEvent -> publishAllowedUserRemoved(event)
            is ParticipantJoinedEvent -> publishParticipantJoined(event)
            is ParticipantLeftEvent -> publishParticipantLeft(event)
        }
    }

    @Topic("session-created") fun publishSessionCreated(event: SessionCreatedEvent)

    @Topic("session-deleted") fun publishSessionDeleted(event: SessionDeletedEvent)

    @Topic("allowed-user-added") fun publishAllowedUserAdded(event: AllowedUserAddedEvent)

    @Topic("allowed-user-removed") fun publishAllowedUserRemoved(event: AllowedUserRemovedEvent)

    @Topic("participant-joined") fun publishParticipantJoined(event: ParticipantJoinedEvent)

    @Topic("participant-left") fun publishParticipantLeft(event: ParticipantLeftEvent)
}
