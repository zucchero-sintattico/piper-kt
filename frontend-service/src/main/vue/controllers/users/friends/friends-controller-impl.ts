import { AxiosController } from "@/controllers/axios-controller";
import type { FriendsController } from "./friends-controller";
import {
  DeclineFriendRequestApi,
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
    to: string
  ): Promise<DeclineFriendRequestApi.Response> {
    const body: DeclineFriendRequestApi.Request.Body = {
      receiver: to,
    };
    return await this.post<DeclineFriendRequestApi.Response>(
      "/friends/requests/send",
      body
    );
  }

  async acceptFriendRequest(
    to: string
  ): Promise<DeclineFriendRequestApi.Response> {
    const body: DeclineFriendRequestApi.Request.Body = {
      sender: to,
    };
    return await this.post<DeclineFriendRequestApi.Response>(
      "/friends/requests/accept",
      body
    );
  }

  async denyFriendRequest(
    to: string
  ): Promise<DeclineFriendRequestApi.Response> {
    const body: DeclineFriendRequestApi.Request.Body = {
      sender: to,
    };
    return await this.post<DeclineFriendRequestApi.Response>(
      "/friends/requests/decline",
      body
    );
  }
}
