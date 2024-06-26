import {
  type Empty,
  ErrorResponse,
  Response,
  type ResponseFacade,
} from "../response";
import { type RequestSchema, EmptySchema } from "../schema";
/* eslint-disable @typescript-eslint/no-namespace */
/**
 * Register endpoint
 */
export module RegisterApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = Empty;
    export type Body = {
      username: string;
      password: string;
      email: string;
      description?: string;
      photo?: {
        data: Buffer;
        contentType: string;
      };
    };
    export const Schema: RequestSchema = {
      Params: {},
      Body: {
        username: "string",
        password: "string",
        email: "string",
        description: "string?",
        photo: "Buffer?",
      },
    };
  }

  export module Responses {
    interface UserLoginResponse {
      username: string;
      email: string;
      description?: string;
      photo?: {
        data: Buffer;
        contentType: string;
      };
    }

    export class Success extends Response {
      statusCode = 200;
      createdUser: UserLoginResponse;
      constructor(user: UserLoginResponse) {
        super();
        this.createdUser = user;
      }
    }

    export type Type = Success;
  }

  export module Errors {
    export class UserAlreadyExists extends ErrorResponse {
      statusCode = 409;
      message = "User already exists" as const;
    }

    export class EmailAlreadyExists extends ErrorResponse {
      statusCode = 409;
      message = "Email already exists" as const;
    }

    export type Type = UserAlreadyExists | EmailAlreadyExists;
  }

  export type Response = Responses.Type | Errors.Type;
}

/**
 * Login endpoint
 */
export module LoginApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = Record<string, unknown>;
    export type Body = {
      username: string;
      password: string;
    };
    export const Schema: RequestSchema = {
      Params: {},
      Body: {
        username: "string",
        password: "string",
      },
    };
  }

  export module Responses {
    export class Success extends Response {
      statusCode = 200;
      message = "Logged in" as const;
      access_token: string;
      constructor(access_token: string) {
        super();
        this.access_token = access_token;
      }
      override send(res: ResponseFacade) {
        res.cookie("access_token", this.access_token, { httpOnly: true });
        super.send(res);
      }
    }

    export type Type = Success;
  }

  export module Errors {
    export class UsernameOrPasswordIncorrect extends ErrorResponse {
      statusCode = 401;
      message = "Username or password incorrect" as const;
    }
    export type Type = UsernameOrPasswordIncorrect;
  }

  export type Response = Responses.Type | Errors.Type;
}

/**
 * Logout endpoint
 */
export module LogoutApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = Empty;
    export type Body = Empty;
    export const Schema = EmptySchema;
  }
  export module Responses {
    export class Success extends Response {
      statusCode = 200;
      message = "Logged out" as const;
      override send(res: ResponseFacade) {
        res.clearCookie("access_token");
        super.send(res);
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

/**
 * Refresh token endpoint
 */
export module RefreshTokenApi {
  export module Request {
    export type Type = Body & Params;
    export type Params = Empty;
    export type Body = Empty;
    export const Schema = EmptySchema;
  }
  export module Responses {
    export class Success extends Response {
      statusCode = 200;
      message = "refresh_token" as const;
      jwt: string;
      constructor(jwt: string) {
        super();
        this.jwt = jwt;
      }
      override send(res: ResponseFacade) {
        res.cookie("access_token", this.jwt, { httpOnly: true });
        super.send(res);
      }
    }
    export type Type = Success;
  }
  export module Errors {
    export class UserNotFound extends ErrorResponse {
      statusCode = 404;
      message = "User not found" as const;
    }
    export class InvalidRefreshToken extends ErrorResponse {
      statusCode = 401;
      message = "Refresh token is missing or invalid" as const;
    }
    export type Type = UserNotFound | InvalidRefreshToken;
  }
  export type Response = Responses.Type | Errors.Type;
}
