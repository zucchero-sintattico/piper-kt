import { piperkt } from "../events-lib";

export class ChannelCreatedMessage extends piperkt.events.ChannelCreatedEvent {
  static topic = "channel-topics";
  static type = "ChannelCreatedEvent";
}

export class ChannelUpdatedMessage extends piperkt.events.ChannelUpdatedEvent {
  static topic = "channel-topics";
  static type = "ChannelUpdated";
}

export class ChannelDeletedMessage extends piperkt.events.ChannelDeletedEvent {
  static topic = "channel-topics";
  static type = "ChannelDeleted";
}

export class NewMessageOnChannelMessage extends piperkt.events
  .MessageInChannelEvent {
  static topic = "channel-topics";
  static type = "MessageInChannelEvent";
}

export class UserJoinedMultimediaChannelMessage extends piperkt.events
  .ParticipantJoinedEvent {
  static topic = "multimedia";
  static type = "ParticipantJoined";
}

export class UserLeftMultimediaChannelMessage extends piperkt.events
  .ParticipantLeftEvent {
  static topic = "multimedia";
  static type = "ParticipantLeft";
}
