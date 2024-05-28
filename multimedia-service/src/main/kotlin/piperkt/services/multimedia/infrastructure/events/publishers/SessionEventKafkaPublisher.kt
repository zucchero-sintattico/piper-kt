package piperkt.services.multimedia.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.events.AllowedUserAddedEvent
import piperkt.events.AllowedUserRemovedEvent
import piperkt.events.ParticipantJoinedEvent
import piperkt.events.ParticipantLeftEvent
import piperkt.events.SessionCreatedEvent
import piperkt.events.SessionDeletedEvent
import piperkt.events.SessionEvent
import piperkt.events.SessionEventPublisher

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

    @Topic(SessionCreatedEvent.TOPIC) fun publishSessionCreated(event: SessionCreatedEvent)

    @Topic(SessionDeletedEvent.TOPIC) fun publishSessionDeleted(event: SessionDeletedEvent)

    @Topic(AllowedUserAddedEvent.TOPIC) fun publishAllowedUserAdded(event: AllowedUserAddedEvent)

    @Topic(AllowedUserRemovedEvent.TOPIC)
    fun publishAllowedUserRemoved(event: AllowedUserRemovedEvent)

    @Topic(ParticipantJoinedEvent.TOPIC) fun publishParticipantJoined(event: ParticipantJoinedEvent)

    @Topic(ParticipantLeftEvent.TOPIC) fun publishParticipantLeft(event: ParticipantLeftEvent)
}
