package piperkt.services.servers.commons.events

interface Event

interface EventPublisher {
    fun publish(event: Event)
}
