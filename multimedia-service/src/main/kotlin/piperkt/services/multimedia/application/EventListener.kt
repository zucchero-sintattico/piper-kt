package piperkt.services.multimedia.application

interface EventListener<E> {
    fun handle(event: E)
}
