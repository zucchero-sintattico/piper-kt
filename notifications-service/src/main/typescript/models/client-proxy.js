"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ClientProxy = void 0;
class ClientProxy {
    constructor(socket) {
        this.socket = socket;
    }
    send(data) {
        this.socket.emit("notification", data);
    }
}
exports.ClientProxy = ClientProxy;
