import { EventsConfiguration } from "./commons/events/events-configuration";
import {
  UserCreatedMessage,
  UserOfflineMessage,
  UserOnlineMessage,
} from "./messages-api/users";
import { Users } from "./models/user";
import { notifiableUsers } from "./models/notification-model";
import {
  ChannelCreatedNotification,
  ChannelDeletedNotification,
  ChannelUpdatedNotification,
  FriendRequestAcceptedNotification,
  FriendRequestNotification,
  NewMessageOnChannelNotification,
  NewMessageOnDirectNotification,
  ServerDeletedNotification,
  ServerUpdatedNotification,
  UserJoinedServerNotification,
  UserLeftServerNotification,
  UserOfflineNotification,
  UserOnlineNotification,
} from "./api/notifications/messages";
import {
  ChannelCreatedMessage,
  ChannelDeletedMessage,
  ChannelUpdatedMessage,
  NewMessageOnChannelMessage,
} from "./messages-api/channels";
import { Servers } from "./models/server";
import { NewMessageInFriendshipMessage } from "./messages-api/directs";
import {
  FriendRequestAcceptedMessage,
  FriendRequestSentMessage,
} from "./messages-api/friends";
import {
  ServerCreatedMessage,
  ServerDeletedMessage,
  ServerUpdatedMessage,
  UserJoinedServerMessage,
  UserKickedFromServerMessage,
  UserLeftServerMessage,
} from "./messages-api/servers";
import { piperkt } from "./events-lib";
import ServerUserAddedEvent = piperkt.events.ServerUserAddedEvent;

export class NotificationsServiceEventsConfiguration extends EventsConfiguration {
  constructor() {
    super();
    // this.listenToServersUpdates();
    this.listenToFriendsRequest();
    this.listenToMessages();
    this.listenToUserStatus();
    // this.listenToChannelsUpdates();
  }

  listenToUserStatus() {
    this.on(UserCreatedMessage, async (event: UserCreatedMessage) => {
      await Users.create({
        username: event.username,
        friends: [],
      });
    });

    // this.on(UserDeletedMessage, async (event: UserDeletedMessage) => {
    //   const user = await Users.findOne({ username: event.username });
    //   const friends = user?.friends;
    //   await Users.deleteOne({ username: event.username });
    //   await Users.updateMany(
    //     { username: { $in: friends } },
    //     { $pull: { friends: event.username } }
    //   );
    // });

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
    this.on(
      NewMessageOnChannelMessage,
      async (event: NewMessageOnChannelMessage) => {
        const server = await Servers.findOne({ _id: event.serverId });
        const participants = server?.participants.filter(
          (participant) => participant !== event.sender
        );
        participants?.forEach((participant) => {
          notifiableUsers.sendIfPresent(
            participant,
            new NewMessageOnChannelNotification({
              from: event.sender,
              content: event.content,
              server: event.serverId,
              channel: event.channelId,
            })
          );
        });
      }
    );

    this.on(
      NewMessageInFriendshipMessage,
      async (event: NewMessageInFriendshipMessage) => {
        notifiableUsers.sendIfPresent(
          event.toUser,
          new NewMessageOnDirectNotification({
            from: event.fromUser,
            content: event.content,
          })
        );
      }
    );
  }

  listenToFriendsRequest() {
    this.on(
      FriendRequestSentMessage,
      async (event: FriendRequestSentMessage) => {
        notifiableUsers.sendIfPresent(
          event.toUser,
          new FriendRequestNotification({
            from: event.fromUser,
          })
        );
      }
    );

    this.on(
      FriendRequestAcceptedMessage,
      async (event: FriendRequestAcceptedMessage) => {
        await Users.updateOne(
          { username: event.fromUser },
          { $push: { friends: event.toUser } }
        );
        await Users.updateOne(
          { username: event.toUser },
          { $push: { friends: event.fromUser } }
        );
        notifiableUsers.sendIfPresent(
          event.toUser,
          new FriendRequestAcceptedNotification({
            from: event.fromUser,
          })
        );
      }
    );
  }

  listenToServersUpdates() {
    this.on(ServerCreatedMessage, async (event: ServerCreatedMessage) => {
      await Servers.create({
        _id: event.serverId,
        owner: event.owner,
        participants: [event.owner],
      });
    });

    this.on(ServerDeletedMessage, async (event: ServerDeletedMessage) => {
      const server = await Servers.findOne({ _id: event.serverId });
      await Servers.deleteOne({ _id: event.serverId });
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new ServerDeletedNotification({
            serverId: event.serverId,
          })
        );
      });
    });

    this.on(ServerUpdatedMessage, async (event: ServerUpdatedMessage) => {
      const server = await Servers.findOne({ _id: event.serverId });
      server?.participants.forEach((participant) => {
        notifiableUsers.sendIfPresent(
          participant,
          new ServerUpdatedNotification({
            serverId: event.serverId,
          })
        );
      });
    });

    this.on(UserJoinedServerMessage, async (event: UserJoinedServerMessage) => {
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

    this.on(UserLeftServerMessage, async (event: UserLeftServerMessage) => {
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

    this.on(
      UserKickedFromServerMessage,
      async (event: UserKickedFromServerMessage) => {
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
      }
    );
  }

  listenToChannelsUpdates() {
    this.on(ChannelCreatedMessage, async (event: ChannelCreatedMessage) => {
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

    this.on(ChannelUpdatedMessage, async (event: ChannelUpdatedMessage) => {
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

    this.on(ChannelDeletedMessage, async (event: ChannelDeletedMessage) => {
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
