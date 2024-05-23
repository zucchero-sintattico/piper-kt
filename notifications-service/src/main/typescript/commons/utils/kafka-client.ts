import { Consumer, Kafka, Producer } from "kafkajs";

export class KafkaClient {
  private static instance: KafkaClient | undefined;

  private brokersUri: string;
  private groupId: string;
  private kafka: Kafka;
  private producer: Producer;
  private consumer: Consumer;

  static async initialize(connectionUri: string) {
    if (!KafkaClient.instance) {
      this.instance = new KafkaClient(connectionUri);
      await this.instance.connect();
      console.log("Connected to Kafka");
    }
  }

  static async disconnect() {
    if (KafkaClient.instance) {
      await KafkaClient.instance.disconnect();
    }
  }

  static getInstance() {
    if (!KafkaClient.instance) {
      console.log(KafkaClient.instance);
      throw new Error("Kafka not initialized");
    }
    return KafkaClient.instance;
  }

  private constructor(brokersUri: string) {
    this.brokersUri = brokersUri;
    this.kafka = new Kafka({ brokers: [this.brokersUri] });
    this.producer = this.kafka.producer();
    // Random groupId to avoid kafka load balancing
    this.groupId = "groupId-" + Math.random().toString(36);
    // Allow auto topic creation - a consumer can subscribe to a topic not created yet
    this.consumer = this.kafka.consumer({
      groupId: this.groupId,
      allowAutoTopicCreation: true,
    });
  }

  async connect() {
    try {
      await this.producer.connect();
      await this.consumer.connect();
    } catch (err) {
      console.error(err);
    }
  }

  getConsumer(): Consumer {
    return this.consumer;
  }

  async disconnect() {
    await this.producer.disconnect();
    await this.consumer.disconnect();
  }

  async publish<T extends object>(
    EventType: { topic: string; type: string },
    message: T
  ) {
    await this.producer.send({
      topic: EventType.topic,
      messages: [{ value: JSON.stringify(message) }],
    });
  }
}
