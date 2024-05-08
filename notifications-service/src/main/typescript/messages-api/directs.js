"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UserLeftDirectCall = exports.UserJoinedDirectCall = exports.NewMessageOnDirect = void 0;
class NewMessageOnDirect {
    constructor(data) {
        this.sender = data.sender;
        this.receiver = data.receiver;
        this.message = data.message;
    }
}
exports.NewMessageOnDirect = NewMessageOnDirect;
NewMessageOnDirect.exchange = "direct";
NewMessageOnDirect.routingKey = "message.new";
class UserJoinedDirectCall {
    constructor(data) {
        this.joiner = data.joiner;
        this.other = data.other;
    }
}
exports.UserJoinedDirectCall = UserJoinedDirectCall;
UserJoinedDirectCall.exchange = "direct";
UserJoinedDirectCall.routingKey = "user.joined";
class UserLeftDirectCall {
    constructor(data) {
        this.leaver = data.leaver;
        this.other = data.other;
    }
}
exports.UserLeftDirectCall = UserLeftDirectCall;
UserLeftDirectCall.exchange = "direct";
UserLeftDirectCall.routingKey = "user.left";
