import {
  GetFriendsApi,
  GetFriendsRequestsApi,
  DeclineFriendRequestApi,
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
  sendFriendRequest(to: string): Promise<DeclineFriendRequestApi.Response>;

  /**
   * Accept a friend request from a user.
   */
  acceptFriendRequest(to: string): Promise<DeclineFriendRequestApi.Response>;

  /**
   * Deny a friend request from a user.
   */
  denyFriendRequest(to: string): Promise<DeclineFriendRequestApi.Response>;
}
