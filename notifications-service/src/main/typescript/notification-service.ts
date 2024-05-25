import {
  NotificationController,
  NotificationControllerImpl,
} from "./controllers/notification-controller";
import { ClientProxy } from "./models/client-proxy";
import { Server, Socket } from "socket.io";
import { decodeAccessToken, isAccessTokenValid } from "./commons/utils/jwt";
import http from "http";

export class NotificationSocketServer {
  private io: Server;
  private notificationController: NotificationController =
    new NotificationControllerImpl();

  constructor(server: http.Server) {
    console.log("Starting notification socket server");
    const test = new Server(server, {
      path: "/webrtc",
    });
    test.on("connection", async (socket) => {
      console.log("New connection on webrtc");
      return socket;
    });
    this.io = new Server(server, {
      path: "/notification",
    });
    this.io.on("connection", async (socket) => {
      const jwt = socket.handshake.auth?.token as string | undefined;
      const username = decodeAccessToken(jwt!).sub;
      if (username) {
        const clientProxy = new ClientProxy(socket);
        socket.on("disconnect", async () => {
          await this.notificationController.unsubscribe(username);
        });
        await this.notificationController.subscribe(username, clientProxy);
      }
    });
  }
}
