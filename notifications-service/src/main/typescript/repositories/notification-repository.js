"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.NotificationRepositoryImpl = void 0;
const notification_model_1 = require("../models/notification-model");
class NotificationRepositoryImpl {
    onNewMessage(message) {
        notification_model_1.notifiableUsers.sendIfPresent(message.to, {
            from: message.from,
            content: message.content,
        });
    }
}
exports.NotificationRepositoryImpl = NotificationRepositoryImpl;
