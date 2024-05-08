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
exports.NotificationsServiceEventsConfiguration = void 0;
const events_configuration_1 = require("./commons/events/events-configuration");
const channels_1 = require("./messages-api/channels");
const directs_1 = require("./messages-api/directs");
const notification_model_1 = require("./models/notification-model");
const messages_1 = require("./api/notifications/messages");
const friends_1 = require("./messages-api/friends");
const servers_1 = require("./messages-api/servers");
const users_1 = require("./messages-api/users");
const server_1 = require("./models/server");
const user_1 = require("./models/user");
class NotificationsServiceEventsConfiguration extends events_configuration_1.EventsConfiguration {
    constructor() {
        super();
        this.listenToServersUpdates();
        this.listenToFriendsRequest();
        this.listenToMessages();
        this.listenToUserStatus();
        this.listenToChannelsUpdates();
    }
    listenToUserStatus() {
        this.on(users_1.UserCreatedMessage, (event) => __awaiter(this, void 0, void 0, function* () {
            yield user_1.Users.create({
                username: event.username,
                friends: [],
            });
        }));
        this.on(users_1.UserDeletedMessage, (event) => __awaiter(this, void 0, void 0, function* () {
            const user = yield user_1.Users.findOne({ username: event.username });
            const friends = user === null || user === void 0 ? void 0 : user.friends;
            yield user_1.Users.deleteOne({ username: event.username });
            yield user_1.Users.updateMany({ username: { $in: friends } }, { $pull: { friends: event.username } });
        }));
        this.on(users_1.UserOnlineMessage, (event) => __awaiter(this, void 0, void 0, function* () {
            const user = yield user_1.Users.findOne({ username: event.username });
            const friends = user === null || user === void 0 ? void 0 : user.friends;
            friends === null || friends === void 0 ? void 0 : friends.forEach((friend) => {
                notification_model_1.notifiableUsers.sendIfPresent(friend, new messages_1.UserOnlineNotification({
                    user: event.username,
                }));
            });
        }));
        this.on(users_1.UserOfflineMessage, (event) => __awaiter(this, void 0, void 0, function* () {
            const user = yield user_1.Users.findOne({ username: event.username });
            const friends = user === null || user === void 0 ? void 0 : user.friends;
            friends === null || friends === void 0 ? void 0 : friends.forEach((friend) => {
                notification_model_1.notifiableUsers.sendIfPresent(friend, new messages_1.UserOfflineNotification({
                    user: event.username,
                }));
            });
        }));
    }
    listenToMessages() {
        this.on(channels_1.NewMessageOnChannel, (event) => __awaiter(this, void 0, void 0, function* () {
            const server = yield server_1.Servers.findOne({ _id: event.serverId });
            const participants = server === null || server === void 0 ? void 0 : server.participants.filter((participant) => participant !== event.sender);
            participants === null || participants === void 0 ? void 0 : participants.forEach((participant) => {
                notification_model_1.notifiableUsers.sendIfPresent(participant, new messages_1.NewMessageOnChannelNotification({
                    from: event.sender,
                    content: event.message,
                    server: event.serverId,
                    channel: event.channelId,
                }));
            });
        }));
        this.on(directs_1.NewMessageOnDirect, (event) => __awaiter(this, void 0, void 0, function* () {
            notification_model_1.notifiableUsers.sendIfPresent(event.receiver, new messages_1.NewMessageOnDirectNotification({
                from: event.sender,
                content: event.message,
            }));
        }));
    }
    listenToFriendsRequest() {
        this.on(friends_1.FriendRequestSentMessage, (event) => __awaiter(this, void 0, void 0, function* () {
            notification_model_1.notifiableUsers.sendIfPresent(event.to, new messages_1.FriendRequestNotification({
                from: event.from,
            }));
        }));
        this.on(friends_1.FriendRequestAcceptedMessage, (event) => __awaiter(this, void 0, void 0, function* () {
            yield user_1.Users.updateOne({ username: event.from }, { $push: { friends: event.to } });
            yield user_1.Users.updateOne({ username: event.to }, { $push: { friends: event.from } });
            notification_model_1.notifiableUsers.sendIfPresent(event.to, new messages_1.FriendRequestAcceptedNotification({
                from: event.from,
            }));
        }));
    }
    listenToServersUpdates() {
        this.on(servers_1.ServerCreated, (event) => __awaiter(this, void 0, void 0, function* () {
            yield server_1.Servers.create({
                _id: event.id,
                owner: event.owner,
                participants: [event.owner],
            });
        }));
        this.on(servers_1.ServerDeleted, (event) => __awaiter(this, void 0, void 0, function* () {
            const server = yield server_1.Servers.findOne({ _id: event.id });
            yield server_1.Servers.deleteOne({ _id: event.id });
            server === null || server === void 0 ? void 0 : server.participants.forEach((participant) => {
                notification_model_1.notifiableUsers.sendIfPresent(participant, new messages_1.ServerDeletedNotification({
                    serverId: event.id,
                }));
            });
        }));
        this.on(servers_1.ServerUpdated, (event) => __awaiter(this, void 0, void 0, function* () {
            const server = yield server_1.Servers.findOne({ _id: event.id });
            server === null || server === void 0 ? void 0 : server.participants.forEach((participant) => {
                notification_model_1.notifiableUsers.sendIfPresent(participant, new messages_1.ServerUpdatedNotification({
                    serverId: event.id,
                }));
            });
        }));
        this.on(servers_1.UserJoinedServer, (event) => __awaiter(this, void 0, void 0, function* () {
            const server = yield server_1.Servers.findOne({ _id: event.serverId });
            yield server_1.Servers.updateOne({ _id: event.serverId }, { $push: { participants: event.username } });
            server === null || server === void 0 ? void 0 : server.participants.forEach((participant) => {
                notification_model_1.notifiableUsers.sendIfPresent(participant, new messages_1.UserJoinedServerNotification({
                    serverId: event.serverId,
                    user: event.username,
                }));
            });
        }));
        this.on(servers_1.UserLeftServer, (event) => __awaiter(this, void 0, void 0, function* () {
            const server = yield server_1.Servers.findOne({ _id: event.serverId });
            yield server_1.Servers.updateOne({ _id: event.serverId }, { $pull: { participants: event.username } });
            server === null || server === void 0 ? void 0 : server.participants.forEach((participant) => {
                notification_model_1.notifiableUsers.sendIfPresent(participant, new messages_1.UserLeftServerNotification({
                    serverId: event.serverId,
                    user: event.username,
                }));
            });
        }));
        this.on(servers_1.UserKickedFromServer, (event) => __awaiter(this, void 0, void 0, function* () {
            const server = yield server_1.Servers.findOne({ _id: event.serverId });
            yield server_1.Servers.updateOne({ _id: event.serverId }, { $pull: { participants: event.username } });
            server === null || server === void 0 ? void 0 : server.participants.forEach((participant) => {
                notification_model_1.notifiableUsers.sendIfPresent(participant, new messages_1.UserLeftServerNotification({
                    serverId: event.serverId,
                    user: event.username,
                }));
            });
        }));
    }
    listenToChannelsUpdates() {
        this.on(channels_1.ChannelCreated, (event) => __awaiter(this, void 0, void 0, function* () {
            const server = yield server_1.Servers.findOne({ _id: event.serverId });
            server === null || server === void 0 ? void 0 : server.participants.forEach((participant) => {
                notification_model_1.notifiableUsers.sendIfPresent(participant, new messages_1.ChannelCreatedNotification({
                    serverId: event.serverId,
                    channel: event.channelId,
                }));
            });
        }));
        this.on(channels_1.ChannelUpdated, (event) => __awaiter(this, void 0, void 0, function* () {
            const server = yield server_1.Servers.findOne({ _id: event.serverId });
            server === null || server === void 0 ? void 0 : server.participants.forEach((participant) => {
                notification_model_1.notifiableUsers.sendIfPresent(participant, new messages_1.ChannelUpdatedNotification({
                    serverId: event.serverId,
                    channel: event.channelId,
                }));
            });
        }));
        this.on(channels_1.ChannelDeleted, (event) => __awaiter(this, void 0, void 0, function* () {
            const server = yield server_1.Servers.findOne({ _id: event.serverId });
            server === null || server === void 0 ? void 0 : server.participants.forEach((participant) => {
                notification_model_1.notifiableUsers.sendIfPresent(participant, new messages_1.ChannelDeletedNotification({
                    serverId: event.serverId,
                    channel: event.channelId,
                }));
            });
        }));
    }
}
exports.NotificationsServiceEventsConfiguration = NotificationsServiceEventsConfiguration;
