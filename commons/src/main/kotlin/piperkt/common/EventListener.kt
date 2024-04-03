package piperkt.common

interface EventListener<E> {
    fun handle(event: E)
}
