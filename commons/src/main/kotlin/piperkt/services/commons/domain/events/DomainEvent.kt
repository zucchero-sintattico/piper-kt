package piperkt.services.commons.domain.events

interface DomainEvent

interface DomainEventPublisher<E : DomainEvent> {
    fun publish(event: E)
}
