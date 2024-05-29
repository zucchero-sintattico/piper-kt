package piperkt.services.multimedia.infrastructure.events.publishers

import jakarta.inject.Singleton
import piperkt.events.AllowedUserAddedEvent
import piperkt.events.AllowedUserRemovedEvent
import piperkt.events.ParticipantJoinedEvent
import piperkt.events.ParticipantLeftEvent
import piperkt.events.SessionCreatedEvent
import piperkt.events.SessionDeletedEvent
import piperkt.events.SessionEvent
import piperkt.events.SessionEventPublisher

/**
 * Publishes session events.
 *
 * @param kafkaPublisher The Kafka publisher to delegate the events to.
 */
@Singleton
class SessionEventPublisherImpl(private val kafkaPublisher: SessionEventKafkaPublisher) :
    SessionEventPublisher {

    override fun publish(event: SessionEvent) {
        when (event) {
            is SessionCreatedEvent -> kafkaPublisher.publishSessionCreated(event)
            is SessionDeletedEvent -> kafkaPublisher.publishSessionDeleted(event)
            is AllowedUserAddedEvent -> kafkaPublisher.publishAllowedUserAdded(event)
            is AllowedUserRemovedEvent -> kafkaPublisher.publishAllowedUserRemoved(event)
            is ParticipantJoinedEvent -> kafkaPublisher.publishParticipantJoined(event)
            is ParticipantLeftEvent -> kafkaPublisher.publishParticipantLeft(event)
        }
    }
}
