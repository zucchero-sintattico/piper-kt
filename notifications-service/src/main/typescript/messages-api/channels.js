"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UserLeftMultimediaChannel = exports.UserJoinedMultimediaChannel = exports.NewMessageOnChannel = exports.ChannelDeleted = exports.ChannelUpdated = exports.ChannelCreated = void 0;
class ChannelCreated {
    constructor(data) {
        this.serverId = data.serverId;
        this.channelId = data.channelId;
        this.name = data.name;
        this.channelType = data.channelType;
        this.description = data.description;
    }
}
exports.ChannelCreated = ChannelCreated;
ChannelCreated.exchange = "channel";
ChannelCreated.routingKey = "channel.created";
class ChannelUpdated {
    constructor(data) {
        this.serverId = data.serverId;
        this.channelId = data.channelId;
        this.name = data.name;
        this.description = data.description;
    }
}
exports.ChannelUpdated = ChannelUpdated;
ChannelUpdated.exchange = "channel";
ChannelUpdated.routingKey = "channel.updated";
class ChannelDeleted {
    constructor(data) {
        this.serverId = data.serverId;
        this.channelId = data.channelId;
    }
}
exports.ChannelDeleted = ChannelDeleted;
ChannelDeleted.exchange = "channel";
ChannelDeleted.routingKey = "channel.deleted";
class NewMessageOnChannel {
    constructor(data) {
        this.serverId = data.serverId;
        this.channelId = data.channelId;
        this.sender = data.sender;
        this.message = data.message;
    }
}
exports.NewMessageOnChannel = NewMessageOnChannel;
NewMessageOnChannel.exchange = "channel";
NewMessageOnChannel.routingKey = "message.new";
class UserJoinedMultimediaChannel {
    constructor(data) {
        this.serverId = data.serverId;
        this.channelId = data.channelId;
        this.username = data.username;
    }
}
exports.UserJoinedMultimediaChannel = UserJoinedMultimediaChannel;
UserJoinedMultimediaChannel.exchange = "multimedia";
UserJoinedMultimediaChannel.routingKey = "user.joined";
class UserLeftMultimediaChannel {
    constructor(data) {
        this.serverId = data.serverId;
        this.channelId = data.channelId;
        this.username = data.username;
    }
}
exports.UserLeftMultimediaChannel = UserLeftMultimediaChannel;
UserLeftMultimediaChannel.exchange = "multimedia";
UserLeftMultimediaChannel.routingKey = "user.left";
