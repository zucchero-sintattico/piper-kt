import { piperkt } from "../events-lib";

export class FriendRequestSentMessage extends piperkt.events
  .FriendshipRequestSentEvent {
  static topic = this.Companion.TOPIC;
}

export class FriendRequestAcceptedMessage extends piperkt.events
  .FriendshipRequestAcceptedEvent {
  static topic = this.Companion.TOPIC;
}

export class FriendRequestDeniedMessage extends piperkt.events
  .FriendshipRequestRejectedEvent {
  static topic = this.Companion.TOPIC;
}
