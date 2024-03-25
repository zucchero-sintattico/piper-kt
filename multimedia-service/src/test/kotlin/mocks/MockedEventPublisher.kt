package mocks

import piperkt.services.multimedia.domain.events.EventPublisher
import piperkt.services.multimedia.domain.events.SessionEvent
import piperkt.services.multimedia.domain.events.SessionEventPublisher

open class MockedEventPublisher<E> : EventPublisher<E> {
    val publishedEvents = mutableListOf<E>()

    override fun publish(event: E) {
        publishedEvents.add(event)
    }

    fun clear() {
        publishedEvents.clear()
    }
}

class MockedSessionEventPublisher : SessionEventPublisher, MockedEventPublisher<SessionEvent>()
