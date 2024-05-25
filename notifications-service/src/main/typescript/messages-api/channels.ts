import { piperkt } from "../events-lib";

export class ChannelCreatedMessage extends piperkt.events.ChannelCreatedEvent {
  static topic = this.Companion.TOPIC;
}

export class ChannelUpdatedMessage extends piperkt.events.ChannelUpdatedEvent {
  static topic = this.Companion.TOPIC;
}

export class ChannelDeletedMessage extends piperkt.events.ChannelDeletedEvent {
  static topic = this.Companion.TOPIC;
}

export class NewMessageOnChannelMessage extends piperkt.events
  .MessageInChannelEvent {
  static topic = this.Companion.TOPIC;
}

export class UserJoinedMultimediaChannelMessage extends piperkt.events
  .ParticipantJoinedEvent {
  static topic = this.Companion.TOPIC;
}

export class UserLeftMultimediaChannelMessage extends piperkt.events
  .ParticipantLeftEvent {
  static topic = this.Companion.TOPIC;
}
