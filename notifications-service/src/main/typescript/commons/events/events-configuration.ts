interface ExchangeConfiguration {
  events: { [key: string]: (data: unknown) => void };
}

export class EventsConfiguration {
  topics: { [key: string]: ExchangeConfiguration } = {};
  async on<T>(
    EventType: { topic: string; type: string },
    callback: (message: T) => void
  ) {
    if (!this.topics[EventType.topic]) {
      this.topics[EventType.topic] = {
        events: {},
      };
    }
    this.topics[EventType.topic].events[EventType.type] = (data: unknown) => {
      try {
        callback(data as T);
      } catch (error) {
        console.log(error);
      }
    };
  }
}
