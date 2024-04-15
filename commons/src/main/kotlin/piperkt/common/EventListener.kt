package piperkt.common

interface EventListener<E : DomainEvent> {
    fun handle(event: E)

    operator fun invoke(event: E) = runCatching { handle(event) }
}
