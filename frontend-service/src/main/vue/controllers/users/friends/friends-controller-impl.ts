import { AxiosController } from "@/controllers/axios-controller";
import type { FriendsController } from "./friends-controller";
import {
  AcceptFriendRequestApi,
  DeclineFriendRequestApi,
  SendFriendRequestApi,
  type GetFriendsApi,
  type GetFriendsRequestsApi,
} from "@api/users/friends";

export class FriendsControllerImpl
  extends AxiosController
  implements FriendsController
{
  async getFriends(): Promise<GetFriendsApi.Response> {
    return await this.get<GetFriendsApi.Response>("/friends");
  }

  async getFriendsRequests(): Promise<GetFriendsRequestsApi.Response> {
    return await this.get<GetFriendsRequestsApi.Response>("/friends/requests");
  }

  async sendFriendRequest(
    receiver: string
  ): Promise<SendFriendRequestApi.Response> {
    const body: SendFriendRequestApi.Request.Body = {
      receiver: receiver,
    };
    return await this.post<SendFriendRequestApi.Response>(
      "/friends/requests/send",
      body
    );
  }

  async acceptFriendRequest(
    sender: string
  ): Promise<AcceptFriendRequestApi.Response> {
    const body: AcceptFriendRequestApi.Request.Body = {
      sender: sender,
    };
    return await this.post<AcceptFriendRequestApi.Response>(
      "/friends/requests/accept",
      body
    );
  }

  async denyFriendRequest(
    sender: string
  ): Promise<DeclineFriendRequestApi.Response> {
    const body: DeclineFriendRequestApi.Request.Body = {
      sender: sender,
    };
    return await this.post<DeclineFriendRequestApi.Response>(
      "/friends/requests/decline",
      body
    );
  }
}
