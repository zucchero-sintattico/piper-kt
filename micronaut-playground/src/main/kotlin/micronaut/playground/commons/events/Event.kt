package micronaut.playground.commons.events

interface Event

interface EventPublisher {
    fun publish(event: Event)
}
