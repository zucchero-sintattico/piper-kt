package piperkt.common

import piperkt.common.events.DomainEvent

interface EventListener<E : DomainEvent> {
    fun handle(event: E)
}
