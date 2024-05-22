import { piperkt } from "../events-lib";

export class UserCreatedMessage extends piperkt.events.UserCreatedEvent {
  static topic = "user-topics";
  static type = "UserCreated";
}

export class UserUpdatedMessage extends piperkt.events.UserUpdatedEvent {
  static topic = "user-topics";
  static type = "UserUpdated";
}

// export class UserDeletedMessage extends piperkt.events.UserDeletedEvent {
//   static topic = "user-topics";
//   static type = "UserDeleted";
// }

export class UserLoggedInMessage extends piperkt.events.UserLoggedInEvent {
  static topic = "user-topics";
  static type = "LoggedIn";
}

export class UserLoggedOutMessage extends piperkt.events.UserLoggedOutEvent {
  static topic = "user-topics";
  static type = "LoggedOut";
}

export class UserOnlineMessage extends piperkt.events.UserOnlineEvent {
  static topic = "user-topics";
  static type = "UserOnline";
}

export class UserOfflineMessage extends piperkt.events.UserOfflineEvent {
  static topic = "user-topics";
  static type = "UserOffline";
}
