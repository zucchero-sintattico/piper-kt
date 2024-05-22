import { EventsConfiguration } from "./events-configuration";
import { KafkaClient } from "../utils/kafka-client";

export class ServiceEvents {
  static async initialize(
    kafkaClient: KafkaClient,
    configuration: EventsConfiguration
  ): Promise<void> {
    const topics = Object.keys(configuration.topics);
    await kafkaClient
      .getConsumer()
      .subscribe({ topics: topics, fromBeginning: false });
    console.log("[KafkaClient] Subscribed to topics: ", topics);
    await kafkaClient.getConsumer().run({
      eachMessage: async ({ topic, message }) => {
        try {
          const payload = JSON.parse(message.value?.toString() || "");
          const event = configuration.topics[topic].events[payload.type];
          if (event) {
            event(payload);
          } else {
            console.error(
              `[KafkaClient] No event handler for event ${payload.type}`
            );
          }
        } catch (error) {
          console.error(error);
        }
      },
    });
  }
}
