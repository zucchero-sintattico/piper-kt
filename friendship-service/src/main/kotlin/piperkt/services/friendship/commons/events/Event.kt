package piperkt.services.friendship.commons.events

interface Event

interface EventPublisher {
    fun publish(event: Event)
}
