"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.FriendRequestDeniedMessage = exports.FriendRequestAcceptedMessage = exports.FriendRequestSentMessage = void 0;
class FriendRequestSentMessage {
    constructor(data) {
        this.from = data.from;
        this.to = data.to;
    }
}
exports.FriendRequestSentMessage = FriendRequestSentMessage;
FriendRequestSentMessage.exchange = "friend";
FriendRequestSentMessage.routingKey = "friend.request.sent";
class FriendRequestAcceptedMessage {
    constructor(data) {
        this.from = data.from;
        this.to = data.to;
    }
}
exports.FriendRequestAcceptedMessage = FriendRequestAcceptedMessage;
FriendRequestAcceptedMessage.exchange = "friend";
FriendRequestAcceptedMessage.routingKey = "friend.request.accepted";
class FriendRequestDeniedMessage {
    constructor(data) {
        this.from = data.from;
        this.to = data.to;
    }
}
exports.FriendRequestDeniedMessage = FriendRequestDeniedMessage;
FriendRequestDeniedMessage.exchange = "friend";
FriendRequestDeniedMessage.routingKey = "friend.request.denied";
