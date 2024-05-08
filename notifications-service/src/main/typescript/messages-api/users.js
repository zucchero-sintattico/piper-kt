"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UserOfflineMessage = exports.UserOnlineMessage = exports.UserLoggedOutMessage = exports.UserLoggedInMessage = exports.UserDeletedMessage = exports.UserUpdatedMessage = exports.UserCreatedMessage = void 0;
class UserCreatedMessage {
    constructor(data) {
        this.username = data.username;
        this.email = data.email;
        this.description = data.description;
        this.profilePicture = data.profilePicture;
    }
}
exports.UserCreatedMessage = UserCreatedMessage;
UserCreatedMessage.exchange = "user";
UserCreatedMessage.routingKey = "user.created";
class UserUpdatedMessage {
    constructor(data) {
        this.username = data.username;
        this.email = data.email;
        this.description = data.description;
        this.profilePicture = data.profilePicture;
    }
}
exports.UserUpdatedMessage = UserUpdatedMessage;
UserUpdatedMessage.exchange = "user";
UserUpdatedMessage.routingKey = "user.updated";
class UserDeletedMessage {
    constructor(data) {
        this.username = data.username;
    }
}
exports.UserDeletedMessage = UserDeletedMessage;
UserDeletedMessage.exchange = "user";
UserDeletedMessage.routingKey = "user.deleted";
class UserLoggedInMessage {
    constructor(data) {
        this.username = data.username;
    }
}
exports.UserLoggedInMessage = UserLoggedInMessage;
UserLoggedInMessage.exchange = "user";
UserLoggedInMessage.routingKey = "user.logged.in";
class UserLoggedOutMessage {
    constructor(data) {
        this.username = data.username;
    }
}
exports.UserLoggedOutMessage = UserLoggedOutMessage;
UserLoggedOutMessage.exchange = "user";
UserLoggedOutMessage.routingKey = "user.logged.out";
class UserOnlineMessage {
    constructor(data) {
        this.username = data.username;
    }
}
exports.UserOnlineMessage = UserOnlineMessage;
UserOnlineMessage.exchange = "user";
UserOnlineMessage.routingKey = "user.online";
class UserOfflineMessage {
    constructor(data) {
        this.username = data.username;
    }
}
exports.UserOfflineMessage = UserOfflineMessage;
UserOfflineMessage.exchange = "user";
UserOfflineMessage.routingKey = "user.offline";
