package piperkt.services.multimedia.common

open class MockedEventPublisher<E : DomainEvent> : EventPublisher<E> {
    val publishedEvents = mutableListOf<E>()

    override fun publish(event: E) {
        publishedEvents.add(event)
    }

    fun clear() {
        publishedEvents.clear()
    }
}
