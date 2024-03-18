package piperkt.services.commons.domain.events

interface DomainEvent

interface DomainEventPublisher {
    fun publish(event: DomainEvent)
}
