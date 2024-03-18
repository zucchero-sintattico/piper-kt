package mocks

import piperkt.services.multimedia.application.DomainEvent
import piperkt.services.multimedia.application.EventPublisher

class MockedEventPublisher : EventPublisher {
    val publishedEvents = mutableListOf<Any>()

    override fun publish(event: DomainEvent) {
        publishedEvents.add(event)
    }

    fun clear() {
        publishedEvents.clear()
    }
}
