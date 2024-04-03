package piperkt.services.multimedia.common

interface EventListener<E> {
    fun handle(event: E)
}
