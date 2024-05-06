package piperkt.common

interface EventPublisher<E : DomainEvent> {
    fun publish(event: E)
}
