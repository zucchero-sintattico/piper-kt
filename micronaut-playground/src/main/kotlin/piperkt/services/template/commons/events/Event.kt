package piperkt.services.template.commons.events

interface Event

interface EventPublisher {
  fun publish(event: Event)
}
