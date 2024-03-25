package piperkt.services.multimedia.domain.events

interface EventListener<E> {
    fun handle(event: E)
}
