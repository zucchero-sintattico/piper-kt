package piperkt.common.events

interface EventListener<E : DomainEvent> {
    fun handle(event: E)

    operator fun invoke(event: E) = runCatching { handle(event) }
}
