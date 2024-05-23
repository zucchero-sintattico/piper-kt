import { piperkt } from "../events-lib";

export class ServerCreatedMessage extends piperkt.events.ServerCreatedEvent {
  static topic = "server-events";
  static type = "ServerCreatedEvent";
}

export class ServerUpdatedMessage extends piperkt.events.ServerUpdatedEvent {
  static topic = "server-events";
  static type = "ServerUpdatedEvent";
}

export class ServerDeletedMessage extends piperkt.events.ServerDeletedEvent {
  static topic = "server-events";
  static type = "ServerDeletedEvent";
}

export class UserJoinedServerMessage extends piperkt.events
  .ServerUserAddedEvent {
  static topic = "server-events";
  static type = "ServerUserAddedEvent";
}

export class UserLeftServerMessage extends piperkt.events
  .ServerUserRemovedEvent {
  static topic = "server-events";
  static type = "ServerUserRemovedEvent";
}

export class UserKickedFromServerMessage extends piperkt.events
  .ServerUserKickedEvent {
  static topic = "server-events";
  static type = "ServerUserKickedEvent";
}
