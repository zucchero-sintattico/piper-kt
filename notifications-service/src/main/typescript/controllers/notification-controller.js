"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.NotificationControllerImpl = void 0;
const notification_model_1 = require("../models/notification-model");
const user_status_repository_1 = require("../repositories/user-status-repository");
const rabbit_mq_1 = require("../commons/utils/rabbit-mq");
const users_1 = require("../messages-api/users");
class NotificationControllerImpl {
    constructor() {
        this.userStatusRepository = new user_status_repository_1.UserStatusRepositoryImpl();
    }
    subscribe(username, clientProxy) {
        return __awaiter(this, void 0, void 0, function* () {
            yield this.userStatusRepository.setOnline(username);
            notification_model_1.notifiableUsers.addUser(username, clientProxy);
            rabbit_mq_1.RabbitMQ.getInstance().publish(users_1.UserOnlineMessage, new users_1.UserOnlineMessage({ username: username }));
            console.log(`Subscribed ${username} to notifications`);
        });
    }
    unsubscribe(username) {
        return __awaiter(this, void 0, void 0, function* () {
            yield this.userStatusRepository.setOffline(username);
            notification_model_1.notifiableUsers.removeUser(username);
            rabbit_mq_1.RabbitMQ.getInstance().publish(users_1.UserOfflineMessage, new users_1.UserOfflineMessage({ username: username }));
            console.log(`Unsubscribed ${username} from notifications`);
        });
    }
}
exports.NotificationControllerImpl = NotificationControllerImpl;
