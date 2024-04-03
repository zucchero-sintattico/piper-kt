package piperkt.services.multimedia.common

interface EventPublisher<E : DomainEvent> {
    fun publish(event: E)
}
