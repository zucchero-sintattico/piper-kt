import { piperkt } from "../events-lib";

export class ServerCreatedMessage extends piperkt.events.ServerCreatedEvent {
  static topic = this.Companion.TOPIC;
}

export class ServerUpdatedMessage extends piperkt.events.ServerUpdatedEvent {
  static topic = this.Companion.TOPIC;
}

export class ServerDeletedMessage extends piperkt.events.ServerDeletedEvent {
  static topic = this.Companion.TOPIC;
}

export class UserJoinedServerMessage extends piperkt.events
  .ServerUserAddedEvent {
  static topic = this.Companion.TOPIC;
}

export class UserLeftServerMessage extends piperkt.events
  .ServerUserRemovedEvent {
  static topic = this.Companion.TOPIC;
}

export class UserKickedFromServerMessage extends piperkt.events
  .ServerUserKickedEvent {
  static topic = this.Companion.TOPIC;
}
