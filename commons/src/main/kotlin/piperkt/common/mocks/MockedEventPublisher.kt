package piperkt.common.mocks

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher

open class MockedEventPublisher<E : DomainEvent> : EventPublisher<E> {
    val publishedEvents = mutableListOf<E>()

    override fun publish(event: E) {
        publishedEvents.add(event)
    }

    fun clear() {
        publishedEvents.clear()
    }
}
