import { BadRequest, InternalServerError } from "@api/errors";
import axios from "axios";

export abstract class AxiosController {
  getAuthorizationToken() {
    const userStorage = localStorage.getItem("user");
    const user = userStorage ? JSON.parse(userStorage) : null;
    const accessToken = user ? user.jwt : null;
    if (accessToken) {
      return {
        Authorization: `Bearer ${accessToken}`,
      };
    }
    return {};
  }

  private async request<Response>(
    method: string,
    path: string,
    data: object,
    headers: object = this.getAuthorizationToken()
  ): Promise<Response> {
    try {
      const response = await axios.request<Response>({
        method: method,
        url: path,
        data: data,
        headers: headers,
      });
      return {
        statusCode: response.status,
        ...response.data,
      } as Response;
    } catch (e: any) {
      if (e.response.data instanceof BadRequest) {
        const errorResponse = e.response.data as BadRequest;
        throw new Error(errorResponse.toString());
      } else if (e.response.data instanceof InternalServerError) {
        const errorResponse = e.response.data as InternalServerError;
        throw new Error(errorResponse.toString());
      } else {
        return {
          statusCode: e.response.status,
          ...e.response.data,
        } as Response;
      }
    }
  }

  protected async post<Success>(
    path: string,
    data: object = {}
  ): Promise<Success> {
    return await this.request<Success>("post", path, data);
  }

  protected async get<Success>(
    path: string,
    data: object = {}
  ): Promise<Success> {
    return await this.request<Success>("get", path, data);
  }

  protected async put<Success>(
    path: string,
    data: object = {},
    headers: object = {}
  ): Promise<Success> {
    return await this.request<Success>("put", path, data, headers);
  }

  protected async delete<Success>(
    path: string,
    data: object = {}
  ): Promise<Success> {
    return await this.request<Success>("delete", path, data);
  }
}
