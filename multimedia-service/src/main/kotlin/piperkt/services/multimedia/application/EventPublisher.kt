package piperkt.services.multimedia.application

interface DomainEvent

interface EventPublisher {
    fun publish(event: DomainEvent)
}

class TestEventPublisher : EventPublisher {
    val publishedEvents = mutableListOf<DomainEvent>()

    override fun publish(event: DomainEvent) {
        publishedEvents.add(event)
    }
}
