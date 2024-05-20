package piperkt.common.events

interface EventPublisher<E : DomainEvent> {
    fun publish(event: E)
}
