package piperkt.common

interface EventListener<E : DomainEvent> {
    fun handle(event: E)
}
