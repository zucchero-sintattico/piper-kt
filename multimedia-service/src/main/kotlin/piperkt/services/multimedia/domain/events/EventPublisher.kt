package piperkt.services.multimedia.domain.events

interface DomainEvent

interface EventPublisher<E> {
    fun publish(event: E)
}
