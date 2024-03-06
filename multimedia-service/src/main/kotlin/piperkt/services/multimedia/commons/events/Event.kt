package piperkt.services.multimedia.commons.events

interface Event

interface EventPublisher {
    fun publish(event: Event)
}
