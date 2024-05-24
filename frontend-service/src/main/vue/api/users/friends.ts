import { ErrorResponse, Response, type Empty } from "../response";
import { EmptySchema, type RequestSchema } from "../schema";
/* eslint-disable @typescript-eslint/no-namespace */

export module GetFriendsApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = Empty;
    export type Body = Empty;
    export const Schema: RequestSchema = EmptySchema;
  }
  export module Responses {
    export class Success extends Response {
      statusCode = 200;
      friends: string[];
      constructor(friends: string[]) {
        super();
        this.friends = friends;
      }
    }
    export type Type = Success;
  }
  export module Errors {
    export class UserNotFound extends ErrorResponse {
      statusCode = 404;
      message = "User not found" as const;
    }
    export type Type = UserNotFound;
  }
  export type Response = Responses.Type | Errors.Type;
}

export module GetFriendsRequestsApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = Empty;
    export type Body = Empty;
    export const Schema: RequestSchema = EmptySchema;
  }
  export module Responses {
    export class Success extends Response {
      statusCode = 200;
      requests: string[];
      constructor(requests: string[]) {
        super();
        this.requests = requests;
      }
    }
    export type Type = Success;
  }
  export module Errors {
    export class UserNotFound extends ErrorResponse {
      statusCode = 404;
      message = "User not found" as const;
    }
    export type Type = UserNotFound;
  }
  export type Response = Responses.Type | Errors.Type;
}

export module SendFriendRequestApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = Empty;
    export type Body = {
      receiver: string;
    };
    export const Schema: RequestSchema = {
      Params: {},
      Body: {
        receiver: "string",
      },
    };
  }
  export module Responses {
    export class Success extends Response {
      statusCode = 200;
      message = "Friend request sent" as const;
    }

    export type Type = Success;
  }
  export module Errors {
    export class InvalidAction extends ErrorResponse {
      statusCode = 400;
      message: string;
      constructor(action: string) {
        super();
        this.message = `Invalid 'action' parameter in body: '${action}'`;
      }
    }
    export class FriendRequestAlreadySent extends ErrorResponse {
      statusCode = 409;
      message = "Friend request already sent" as const;
    }
    export class FriendRequestNotFound extends ErrorResponse {
      statusCode = 404;
      message = "Friend request not found" as const;
    }

    export type Type =
      | InvalidAction
      | FriendRequestAlreadySent
      | FriendRequestNotFound;
  }
  export type Response = Responses.Type | Errors.Type;
}

export module AcceptFriendRequestApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = Empty;
    export type Body = {
      sender: string;
    };
    export const Schema: RequestSchema = {
      Params: {},
      Body: {
        sender: "string",
      },
    };
  }
  export module Responses {
    export class FriendRequestAccepted extends Response {
      statusCode = 200;
      message = "Friend request accepted succesfully" as const;
    }

    export type Type = FriendRequestAccepted;
  }
  export module Errors {
    export class InvalidAction extends ErrorResponse {
      statusCode = 400;
      message: string;
      constructor(action: string) {
        super();
        this.message = `Invalid 'action' parameter in body: '${action}'`;
      }
    }
    export class FriendRequestNotFound extends ErrorResponse {
      statusCode = 404;
      message = "Friend request not found" as const;
    }

    export type Type = FriendRequestNotFound;
  }
  export type Response = Responses.Type | Errors.Type;
}

export module DeclineFriendRequestApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = Empty;
    export type Body = {
      sender: string;
    };
    export const Schema: RequestSchema = {
      Params: {},
      Body: {
        sender: "string",
      },
    };
  }
  export module Responses {
    export class FriendRequestDenied extends Response {
      statusCode = 200;
      message = "Friend request denied successfully" as const;
    }

    export type Type = FriendRequestDenied;
  }
  export module Errors {
    export class InvalidAction extends ErrorResponse {
      statusCode = 400;
      message: string;
      constructor(action: string) {
        super();
        this.message = `Invalid 'action' parameter in body: '${action}'`;
      }
    }

    export class FriendRequestNotFound extends ErrorResponse {
      statusCode = 404;
      message = "Friend request not found" as const;
    }

    export type Type = InvalidAction | FriendRequestNotFound;
  }
  export type Response = Responses.Type | Errors.Type;
}
