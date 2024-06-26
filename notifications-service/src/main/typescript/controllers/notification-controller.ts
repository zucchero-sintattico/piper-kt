import { notifiableUsers } from "../models/notification-model";
import {
  UserStatusRepository,
  UserStatusRepositoryImpl,
} from "../repositories/user-status-repository";
import { ClientProxy } from "../models/client-proxy";
import { KafkaClient } from "../commons/utils/kafka-client";
import { UserOnlineMessage, UserOfflineMessage } from "../messages-api/users";

export interface NotificationController {
  /**
   * Subscribe a client to receive notifications.
   * @param username The username of the client.
   * @param clientProxy The client proxy.
   */
  subscribe(username: string, clientProxy: ClientProxy): Promise<void>;

  /**
   * Unsubscribe a client from receiving notifications.
   * @param username The username of the client.
   */
  unsubscribe(username: string): Promise<void>;
}

export class NotificationControllerImpl implements NotificationController {
  private userStatusRepository: UserStatusRepository =
    new UserStatusRepositoryImpl();

  async subscribe(username: string, clientProxy: ClientProxy): Promise<void> {
    await this.userStatusRepository.setOnline(username);
    notifiableUsers.addUser(username, clientProxy);
    await KafkaClient.getInstance().publish(
      UserOnlineMessage,
      new UserOnlineMessage(username)
    );
    console.log(`Subscribed ${username} to notifications`);
  }

  async unsubscribe(username: string): Promise<void> {
    await this.userStatusRepository.setOffline(username);
    notifiableUsers.removeUser(username);
    await KafkaClient.getInstance().publish(
      UserOfflineMessage,
      new UserOfflineMessage(username)
    );
    console.log(`Unsubscribed ${username} from notifications`);
  }
}
