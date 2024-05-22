import { piperkt } from "../events-lib";

export class ServerCreatedMessage extends piperkt.events.ServerCreatedEvent {
  static topic = "server";
  static type = "ServerCreatedEvent";
}

export class ServerUpdatedMessage extends piperkt.events.ServerUpdatedEvent {
  static topic = "server";
  static type = "ServerUpdatedEvent";
}

export class ServerDeletedMessage extends piperkt.events.ServerDeletedEvent {
  static topic = "server";
  static type = "ServerDeletedEvent";
}

// TODO
export class UserJoinedServerMessage {
  static topic = "server";
  static type = "user.joined";
}

// TODO
export class UserLeftServerMessage {
  static topic = "server";
  static type = "user.left";
}

export class UserKickedFromServerMessage extends piperkt.events
  .ServerUserKickedEvent {
  static topic = "server";
  static type = "ServerUserKickedEvent";
}
