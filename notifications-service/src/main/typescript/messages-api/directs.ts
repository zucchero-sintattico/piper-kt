import { piperkt } from "../events-lib";

// TODO
export class NewMessageInFriendshipMessage extends piperkt.events
  .NewMessageInFriendshipEvent {
  static topic = "direct";
  static type = "NewMessageInFriendshipEvent";
}

// TODO
export class UserJoinedDirectCallMessage {
  static topic = "direct";
  static type = "NewMessageInFriendshipEvent";
}

// TODO
export class UserLeftDirectCallMessage {
  static topic = "direct";
  static type = "NewMessageInFriendshipEvent";
}
