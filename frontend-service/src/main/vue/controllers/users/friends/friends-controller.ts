import {
  GetFriendsApi,
  GetFriendsRequestsApi,
  DeclineFriendRequestApi,
  SendFriendRequestApi,
  AcceptFriendRequestApi,
} from "@api/users/friends";
export interface FriendsController {
  /**
   * Get the friends of a user.
   * @returns The friends of the user.
   */
  getFriends(): Promise<GetFriendsApi.Response>;

  /**
   * Get friend's requests
   * @returns The friend's requests of the user.
   */
  getFriendsRequests(): Promise<GetFriendsRequestsApi.Response>;

  /**
   * Send a friend request to a user.
   */
  sendFriendRequest(receiver: string): Promise<SendFriendRequestApi.Response>;

  /**
   * Accept a friend request from a user.
   */
  acceptFriendRequest(sender: string): Promise<AcceptFriendRequestApi.Response>;

  /**
   * Deny a friend request from a user.
   */
  denyFriendRequest(sender: string): Promise<DeclineFriendRequestApi.Response>;
}
