/* eslint-disable @typescript-eslint/no-namespace */
import { Empty, ErrorResponse, Response } from "../response";
import { EmptySchema, RequestSchema } from "../schema";

/**
 * Get user status
 */
export module GetUserStatusApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = {
      username: string;
    };
    export type Body = Empty;
    export const Schema: RequestSchema = {
      Params: {
        username: "string",
      },
      Body: {},
    };
  }
  export module Responses {
    export type UserStatusInfo = {
      online: boolean;
      lastActive: Date;
    };

    export class Success extends Response {
      statusCode = 200;
      status: UserStatusInfo;
      constructor(status: UserStatusInfo) {
        super();
        this.status = status;
      }
    }

    export type Type = Success;
  }
  export module Errors {
    export class UserNotFound extends ErrorResponse {
      statusCode = 404;
      error = "User not found" as const;
    }
    export type Type = UserNotFound;
  }
  export type Response = Responses.Type | Errors.Type;
}
