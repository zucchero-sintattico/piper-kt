"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.NotificationSocketServer = void 0;
const notification_controller_1 = require("./controllers/notification-controller");
const client_proxy_1 = require("./models/client-proxy");
const socket_io_1 = require("socket.io");
const jwt_1 = require("./commons/utils/jwt");
class NotificationSocketServer {
    constructor(server) {
        this.notificationController = new notification_controller_1.NotificationControllerImpl();
        console.log("Starting notification socket server");
        const test = new socket_io_1.Server(server, {
            path: "/webrtc",
        });
        test.on("connection", (socket) => __awaiter(this, void 0, void 0, function* () {
            console.log("New connection on webrtc");
            return socket;
        }));
        this.io = new socket_io_1.Server(server, {
            path: "/notification",
        });
        this.io.on("connection", (socket) => __awaiter(this, void 0, void 0, function* () {
            var _a;
            const jwt = this.getTokenFromHeadersCookie(socket.handshake.headers.cookie);
            yield this.validateTokenOrDisconnect(socket, jwt);
            const username = (_a = (0, jwt_1.decodeAccessToken)(jwt)) === null || _a === void 0 ? void 0 : _a.username;
            if (username) {
                const clientProxy = new client_proxy_1.ClientProxy(socket);
                socket.on("disconnect", () => __awaiter(this, void 0, void 0, function* () {
                    yield this.notificationController.unsubscribe(username);
                }));
                yield this.notificationController.subscribe(username, clientProxy);
            }
        }));
    }
    getTokenFromHeadersCookie(headers) {
        var _a;
        return (_a = headers === null || headers === void 0 ? void 0 : headers.split(";").find((c) => c.includes("jwt"))) === null || _a === void 0 ? void 0 : _a.split("=")[1];
    }
    validateTokenOrDisconnect(socket, token) {
        return __awaiter(this, void 0, void 0, function* () {
            if (token && !(0, jwt_1.isAccessTokenValid)(token)) {
                console.log("Invalid token");
                socket.disconnect();
                return;
            }
        });
    }
}
exports.NotificationSocketServer = NotificationSocketServer;
