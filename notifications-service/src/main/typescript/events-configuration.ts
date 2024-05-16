import { EventsConfiguration } from "./commons/events/events-configuration";
import {
  ChannelCreated,
  ChannelDeleted,
  ChannelUpdated,
  NewMessageOnChannel,
} from "./messages-api/channels";
import { NewMessageOnDirect } from "./messages-api/directs";
import { notifiableUsers } from "./models/notification-model";
import {
  FriendRequestAcceptedNotification,
  FriendRequestNotification,
  NewMessageOnDirectNotification,
  NewMessageOnChannelNotification,
  UserOnlineNotification,
  UserOfflineNotification,
  ServerDeletedNotification,
  UserLeftServerNotification,
  UserJoinedServerNotification,
  ServerUpdatedNotification,
  ChannelCreatedNotification,
  ChannelUpdatedNotification,
  ChannelDeletedNotification,
} from "./api/notifications/messages";
import {
  FriendRequestSentMessage,
  FriendRequestAcceptedMessage,
} from "./messages-api/friends";
import {
  ServerCreated,
  ServerDeleted,
  UserJoinedServer,
  UserLeftServer,
  UserKickedFromServer,
  ServerUpdated,
} from "./messages-api/servers";
import {
  UserOnlineMessage,
  UserOfflineMessage,
  UserCreatedMessage,
  UserDeletedMessage,
} from "./messages-api/users";
import { Servers } from "./models/server";
import { Users } from "./models/user";

export class NotificationsServiceEventsConfiguration extends EventsConfiguration {
  constructor() {
    super();
    this.listenToServersUpdates();
    this.listenToFriendsRequest();
    this.listenToMessages();
    this.listenToUserStatus();
    this.listenToChannelsUpdates();
  }

  listenToUserStatus() {
    this.on(UserCreatedMessage, async (event: UserCreatedMessage) => {
      await Users.create({
        username: event.username,
        friends: [],
      });
    });

    this.on(UserDeletedMessage, async (event: UserDeletedMessage) => {
      const user = await Users.findOne({ username: event.username });
      const friends = user?.friends;
      await Users.deleteOne({ username: event.username });
      await Users.updateMany(
        { username: { $in: friends } },
        { $pull: { friends: event.username } }
      );
    });

    this.on(UserOnlineMessage, async (event: UserOnlineMessage) => {
      const user = await Users.findOne({ username: event.username });
      const friends = user?.friends;
      friends?.forEach((friend) => {
        notifiableUsers.sendIfPresent(
          friend,
          new UserOnlineNotification({
            user: event.username,
          })
        );
      });
    });

    this.on(UserOfflineMessage, async (event: UserOfflineMessage) => {
      const user = await Users.findOne({ username: event.username });
      const friends = user?.friends;
      friends?.forEach((friend) => {
        notifiableUsers.sendIfPresent(
          friend,
          new UserOfflineNotification({
            user: event.username,
          })
        );
      });
    });
  }

  listenToMessages() {
    this.on(NewMessageOnChannel, async (event: NewMessageOnChannel) => {
      const server = await Servers.findOne({ _id: event.serverId });
      const participants = server?.participants.filter(
        (participant) => participant !== event.sender
      );
      participants?.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new NewMessageOnChannelNotification({
            from: event.sender,
            content: event.message,
            server: event.serverId,
            channel: event.channelId,
          })
        );
      });
    });

    this.on(NewMessageOnDirect, async (event: NewMessageOnDirect) => {
      notifiableUsers.sendIfPresent(
        event.receiver,
        new NewMessageOnDirectNotification({
          from: event.sender,
          content: event.message,
        })
      );
    });
  }

  listenToFriendsRequest() {
    this.on(
      FriendRequestSentMessage,
      async (event: FriendRequestSentMessage) => {
        notifiableUsers.sendIfPresent(
          event.to,
          new FriendRequestNotification({
            from: event.from,
          })
        );
      }
    );

    this.on(
      FriendRequestAcceptedMessage,
      async (event: FriendRequestAcceptedMessage) => {
        await Users.updateOne(
          { username: event.from },
          { $push: { friends: event.to } }
        );
        await Users.updateOne(
          { username: event.to },
          { $push: { friends: event.from } }
        );
        notifiableUsers.sendIfPresent(
          event.to,
          new FriendRequestAcceptedNotification({
            from: event.from,
          })
        );
      }
    );
  }

  listenToServersUpdates() {
    this.on(ServerCreated, async (event: ServerCreated) => {
      await Servers.create({
        _id: event.id,
        owner: event.owner,
        participants: [event.owner],
      });
    });

    this.on(ServerDeleted, async (event: ServerDeleted) => {
      const server = await Servers.findOne({ _id: event.id });
      await Servers.deleteOne({ _id: event.id });
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new ServerDeletedNotification({
            serverId: event.id,
          })
        );
      });
    });

    this.on(ServerUpdated, async (event: ServerUpdated) => {
      const server = await Servers.findOne({ _id: event.id });
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new ServerUpdatedNotification({
            serverId: event.id,
          })
        );
      });
    });

    this.on(UserJoinedServer, async (event: UserJoinedServer) => {
      const server = await Servers.findOne({ _id: event.serverId });
      await Servers.updateOne(
        { _id: event.serverId },
        { $push: { participants: event.username } }
      );
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new UserJoinedServerNotification({
            serverId: event.serverId,
            user: event.username,
          })
        );
      });
    });

    this.on(UserLeftServer, async (event: UserLeftServer) => {
      const server = await Servers.findOne({ _id: event.serverId });
      await Servers.updateOne(
        { _id: event.serverId },
        { $pull: { participants: event.username } }
      );
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new UserLeftServerNotification({
            serverId: event.serverId,
            user: event.username,
          })
        );
      });
    });

    this.on(UserKickedFromServer, async (event: UserKickedFromServer) => {
      const server = await Servers.findOne({ _id: event.serverId });
      await Servers.updateOne(
        { _id: event.serverId },
        { $pull: { participants: event.username } }
      );
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new UserLeftServerNotification({
            serverId: event.serverId,
            user: event.username,
          })
        );
      });
    });
  }

  listenToChannelsUpdates() {
    this.on(ChannelCreated, async (event: ChannelCreated) => {
      const server = await Servers.findOne({ _id: event.serverId });
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new ChannelCreatedNotification({
            serverId: event.serverId,
            channel: event.channelId,
          })
        );
      });
    });

    this.on(ChannelUpdated, async (event: ChannelUpdated) => {
      const server = await Servers.findOne({ _id: event.serverId });
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new ChannelUpdatedNotification({
            serverId: event.serverId,
            channel: event.channelId,
          })
        );
      });
    });

    this.on(ChannelDeleted, async (event: ChannelDeleted) => {
      const server = await Servers.findOne({ _id: event.serverId });
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new ChannelDeletedNotification({
            serverId: event.serverId,
            channel: event.channelId,
          })
        );
      });
    });
  }
}