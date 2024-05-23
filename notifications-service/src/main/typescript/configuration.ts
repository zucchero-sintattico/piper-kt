import {
  DefaultMiddlewares,
  MicroserviceConfiguration,
} from "./commons/service";
import { NotificationsServiceEventsConfiguration } from "./events-configuration";
import { serviceRouter } from "./routes/router";
import { NotificationSocketServer } from "./notification-service";
import http from "http";

const NotificationService = (server: http.Server) => {
  new NotificationSocketServer(server);
};

export const NotificationsServiceConfiguration: MicroserviceConfiguration = {
  port: Number.parseInt(process.env.PORT!) || 3000,
  brokerUri: process.env.BROKER_URI || "localhost:9094",
  mongoUri: process.env.MONGO_URI || "mongodb://localhost:27017/",
  eventsConfiguration: new NotificationsServiceEventsConfiguration(),
  expressConfiguration: {
    middlewares: DefaultMiddlewares,
    serviceRouter: serviceRouter,
    serverBasedServices: [NotificationService],
  },
};
