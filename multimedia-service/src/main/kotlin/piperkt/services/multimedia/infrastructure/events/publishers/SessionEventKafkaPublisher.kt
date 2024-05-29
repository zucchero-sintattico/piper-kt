package piperkt.services.multimedia.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.events.AllowedUserAddedEvent
import piperkt.events.AllowedUserRemovedEvent
import piperkt.events.ParticipantJoinedEvent
import piperkt.events.ParticipantLeftEvent
import piperkt.events.SessionCreatedEvent
import piperkt.events.SessionDeletedEvent

/** Publishes session events to Kafka. */
@KafkaClient
interface SessionEventKafkaPublisher {
    @Topic(SessionCreatedEvent.TOPIC) fun publishSessionCreated(event: SessionCreatedEvent)

    @Topic(SessionDeletedEvent.TOPIC) fun publishSessionDeleted(event: SessionDeletedEvent)

    @Topic(AllowedUserAddedEvent.TOPIC) fun publishAllowedUserAdded(event: AllowedUserAddedEvent)

    @Topic(AllowedUserRemovedEvent.TOPIC)
    fun publishAllowedUserRemoved(event: AllowedUserRemovedEvent)

    @Topic(ParticipantJoinedEvent.TOPIC) fun publishParticipantJoined(event: ParticipantJoinedEvent)

    @Topic(ParticipantLeftEvent.TOPIC) fun publishParticipantLeft(event: ParticipantLeftEvent)
}
