package piperkt.common.events

interface DomainEvent

interface DomainEventPublisher<E : DomainEvent> {
    fun publish(event: E)
}
