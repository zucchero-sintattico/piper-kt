"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ChannelUpdatedNotification = exports.ChannelDeletedNotification = exports.ChannelCreatedNotification = exports.UserLeftServerNotification = exports.UserJoinedServerNotification = exports.ServerUpdatedNotification = exports.ServerDeletedNotification = exports.UserOfflineNotification = exports.UserOnlineNotification = exports.FriendRequestAcceptedNotification = exports.FriendRequestNotification = exports.NewMessageOnChannelNotification = exports.NewMessageOnDirectNotification = void 0;
class NewMessageOnDirectNotification {
    constructor(data) {
        this.type = NewMessageOnDirectNotification.type;
        this.from = data.from;
        this.content = data.content;
    }
}
exports.NewMessageOnDirectNotification = NewMessageOnDirectNotification;
NewMessageOnDirectNotification.type = "new-direct-message";
class NewMessageOnChannelNotification {
    constructor(data) {
        this.type = NewMessageOnChannelNotification.type;
        this.from = data.from;
        this.content = data.content;
        this.server = data.server;
        this.channel = data.channel;
    }
}
exports.NewMessageOnChannelNotification = NewMessageOnChannelNotification;
NewMessageOnChannelNotification.type = "new-channel-message";
class FriendRequestNotification {
    constructor(data) {
        this.type = FriendRequestNotification.type;
        this.from = data.from;
    }
}
exports.FriendRequestNotification = FriendRequestNotification;
FriendRequestNotification.type = "friend-request";
class FriendRequestAcceptedNotification {
    constructor(data) {
        this.type = FriendRequestAcceptedNotification.type;
        this.from = data.from;
    }
}
exports.FriendRequestAcceptedNotification = FriendRequestAcceptedNotification;
FriendRequestAcceptedNotification.type = "friend-request-accepted";
class UserOnlineNotification {
    constructor(data) {
        this.type = UserOnlineNotification.type;
        this.user = data.user;
    }
}
exports.UserOnlineNotification = UserOnlineNotification;
UserOnlineNotification.type = "user-online";
class UserOfflineNotification {
    constructor(data) {
        this.type = UserOfflineNotification.type;
        this.user = data.user;
    }
}
exports.UserOfflineNotification = UserOfflineNotification;
UserOfflineNotification.type = "user-offline";
class ServerDeletedNotification {
    constructor(data) {
        this.type = ServerDeletedNotification.type;
        this.serverId = data.serverId;
    }
}
exports.ServerDeletedNotification = ServerDeletedNotification;
ServerDeletedNotification.type = "server-deleted";
class ServerUpdatedNotification {
    constructor(data) {
        this.type = ServerUpdatedNotification.type;
        this.serverId = data.serverId;
    }
}
exports.ServerUpdatedNotification = ServerUpdatedNotification;
ServerUpdatedNotification.type = "server-updated";
class UserJoinedServerNotification {
    constructor(data) {
        this.type = UserJoinedServerNotification.type;
        this.serverId = data.serverId;
        this.user = data.user;
    }
}
exports.UserJoinedServerNotification = UserJoinedServerNotification;
UserJoinedServerNotification.type = "user-joined-server";
class UserLeftServerNotification {
    constructor(data) {
        this.serverId = data.serverId;
        this.user = data.user;
    }
}
exports.UserLeftServerNotification = UserLeftServerNotification;
UserLeftServerNotification.type = "user-left-server";
class ChannelCreatedNotification {
    constructor(data) {
        this.type = ChannelCreatedNotification.type;
        this.serverId = data.serverId;
        this.channel = data.channel;
    }
}
exports.ChannelCreatedNotification = ChannelCreatedNotification;
ChannelCreatedNotification.type = "channel-created";
class ChannelDeletedNotification {
    constructor(data) {
        this.type = ChannelDeletedNotification.type;
        this.serverId = data.serverId;
        this.channel = data.channel;
    }
}
exports.ChannelDeletedNotification = ChannelDeletedNotification;
ChannelDeletedNotification.type = "channel-deleted";
class ChannelUpdatedNotification {
    constructor(data) {
        this.type = ChannelUpdatedNotification.type;
        this.serverId = data.serverId;
        this.channel = data.channel;
    }
}
exports.ChannelUpdatedNotification = ChannelUpdatedNotification;
ChannelUpdatedNotification.type = "channel-updated";
