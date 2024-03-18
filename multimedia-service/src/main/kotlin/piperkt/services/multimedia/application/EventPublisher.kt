package piperkt.services.multimedia.application

interface DomainEvent

interface EventPublisher {
    fun publish(event: DomainEvent)
}
