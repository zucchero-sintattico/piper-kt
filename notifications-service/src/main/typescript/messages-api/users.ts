import { piperkt } from "../events-lib";

export class UserCreatedMessage extends piperkt.events.UserCreatedEvent {
  static topic = this.Companion.TOPIC;
}

export class UserUpdatedMessage extends piperkt.events.UserUpdatedEvent {
  static topic = this.Companion.TOPIC;
}

// export class UserDeletedMessage extends piperkt.events.UserDeletedEvent {
//   static topic = "user-topics";
//   static type = "UserDeleted";
// }

export class UserLoggedInMessage extends piperkt.events.UserLoggedInEvent {
  static topic = this.Companion.TOPIC;
}

export class UserLoggedOutMessage extends piperkt.events.UserLoggedOutEvent {
  static topic = this.Companion.TOPIC;
}

export class UserOnlineMessage extends piperkt.events.UserOnlineEvent {
  static topic = this.Companion.TOPIC;
}

export class UserOfflineMessage extends piperkt.events.UserOfflineEvent {
  static topic = this.Companion.TOPIC;
}
