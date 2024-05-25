export class EventsConfiguration {
  topics: { [key: string]: (data: unknown) => void } = {};
  async on<T>(EventType: { topic: string }, callback: (message: T) => void) {
    this.topics[EventType.topic] = (data: unknown) => {
      try {
        callback(data as T);
      } catch (error) {
        console.log(error);
      }
    };
  }
}
