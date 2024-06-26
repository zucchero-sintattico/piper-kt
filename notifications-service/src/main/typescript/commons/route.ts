/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @typescript-eslint/no-unused-vars */
import {
  Router,
  Request,
  Response as ExpressResponse,
  Response,
  NextFunction,
} from "express";
import { Validate } from "../api/validate";
import { InternalServerError } from "../api/errors";
import { Response as ApiResponse } from "../api/response";
import { RequestSchema } from "../api/schema";

declare global {
  // eslint-disable-next-line @typescript-eslint/no-namespace
  namespace Express {
    export interface Response<
      ResBody extends ApiResponse = any,
      Locals extends Record<string, any> = Record<string, any>
    > {
      sendResponse(res: ResBody): void;
    }
  }
}

interface EnrichedResponse<
  ResBody extends ApiResponse = any,
  Locals extends Record<string, any> = Record<string, any>
> extends ExpressResponse<ResBody, Locals> {
  sendResponse(res: ResBody): void;
}

type Exception<P, R extends ApiResponse, B, Q> = {
  exception: any;
  onException: (
    e: any,
    req: Request<P, R, B, Q>,
    res: EnrichedResponse<R>
  ) => void;
};
type Middleware<P, R extends ApiResponse, B, Q> = (
  req: Request<P, R, B, Q>,
  res: EnrichedResponse<R>,
  next: () => void
) => void;

export class Route<
  R extends ApiResponse = ApiResponse,
  P extends Record<string, unknown> = Record<string, unknown>,
  B extends Record<string, unknown> = Record<string, unknown>,
  Q extends Record<string, unknown> = Record<string, unknown>
> {
  method: "get" | "post" | "put" | "delete";
  path: string;
  schema: RequestSchema;
  middlewares?: Middleware<any, any, any, any>[];
  handler: (
    req: Request<P, R, B, Q>,
    res: EnrichedResponse<R>
  ) => Promise<void>;
  exceptions?: Exception<P, R, B, Q>[];
  constructor(route: {
    method: "get" | "post" | "put" | "delete";
    path: string;
    schema: RequestSchema;
    middlewares?: Middleware<any, any, any, any>[];
    handler: (
      req: Request<P, R, B, Q>,
      res: EnrichedResponse<R>
    ) => Promise<void>;
    exceptions?: Exception<P, R, B, Q>[];
  }) {
    this.method = route.method;
    this.path = route.path;
    this.schema = route.schema;
    this.middlewares = route.middlewares;
    this.handler = route.handler;
    this.exceptions = route.exceptions;
  }

  attachToRouter(router: Router): void {
    router[this.method](
      this.path,
      (
        req: Request<P, R, B, Q>,
        res: EnrichedResponse<R>,
        next: NextFunction
      ) => {
        res.sendResponse = (response: R) => {
          response.send(res);
        };
        next();
      },
      ...(this.middlewares || []),
      Validate(this.schema),
      async (req: Request<P, R, B, Q>, res: EnrichedResponse<R>) => {
        try {
          await this.handler(req, res);
        } catch (e) {
          if (this.exceptions) {
            const exception = this.exceptions.find((exception) => {
              return e instanceof exception.exception;
            });
            if (exception) {
              exception.onException(e, req, res);
            } else {
              const response = new InternalServerError(e);
              response.send(res);
            }
          } else {
            const response = new InternalServerError(e);
            response.send(res);
          }
        }
      }
    );
  }
}
