package piperkt.services.commons.domain.events

interface Event

interface EventPublisher {
    fun publish(event: Event)
}
