"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.notifiableUsers = void 0;
class NotifiableUsers {
    constructor() {
        this.users = new Map();
    }
    addUser(username, clientProxy) {
        this.users.set(username, clientProxy);
    }
    removeUser(username) {
        this.users.delete(username);
    }
    sendIfPresent(username, data) {
        var _a;
        (_a = this.users.get(username)) === null || _a === void 0 ? void 0 : _a.send(data);
    }
}
exports.notifiableUsers = new NotifiableUsers();
