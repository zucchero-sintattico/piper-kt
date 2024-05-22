import { piperkt } from "../events-lib";

export class FriendRequestSentMessage extends piperkt.events
  .FriendshipRequestSentEvent {
  static topic = "friend";
  static type = "FriendshipRequestSentEvent";
}

export class FriendRequestAcceptedMessage extends piperkt.events
  .FriendshipRequestAcceptedEvent {
  static topic = "friend";
  static type = "FriendshipRequestAcceptedEvent";
}

export class FriendRequestDeniedMessage extends piperkt.events
  .FriendshipRequestRejectedEvent {
  static topic = "friend";
  static type = "FriendshipRequestRejectedEvent";
}
