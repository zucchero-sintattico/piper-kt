import { Mongoose } from "mongoose";

export class MongooseUtils {
  static async initialize(mongoose: Mongoose, connectionUri: string) {
    await mongoose.connect(connectionUri);
    console.log("Connected to MongoDB");
  }

  static async close(mongoose: Mongoose) {
    await mongoose.disconnect();
  }

  static async clear(mongoose: Mongoose) {
    const collections = mongoose.connection.collections;
    for (const key in collections) {
      const collection = collections[key];
      await collection?.deleteMany({});
    }
  }
}
