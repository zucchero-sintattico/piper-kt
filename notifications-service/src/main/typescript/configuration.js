"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.NotificationsServiceConfiguration = void 0;
const service_1 = require("./commons/service");
const events_configuration_1 = require("./events-configuration");
const router_1 = require("./routes/router");
const notification_service_1 = require("./notification-service");
const NotificationService = (server) => {
    new notification_service_1.NotificationSocketServer(server);
};
exports.NotificationsServiceConfiguration = {
    port: Number.parseInt(process.env.PORT) || 3000,
    amqpUri: process.env.AMQP_URI || "amqp://localhost:5672",
    mongoUri: process.env.MONGO_URI || "mongodb://localhost:27017/notifications",
    eventsConfiguration: new events_configuration_1.NotificationsServiceEventsConfiguration(),
    expressConfiguration: {
        middlewares: service_1.DefaultMiddlewares,
        serviceRouter: router_1.serviceRouter,
        serverBasedServices: [NotificationService],
    },
};
