package piperkt.common

import piperkt.common.events.DomainEvent

interface EventPublisher<E : DomainEvent> {
    fun publish(event: E)
}
