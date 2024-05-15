"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UserKickedFromServer = exports.UserLeftServer = exports.UserJoinedServer = exports.ServerDeleted = exports.ServerUpdated = exports.ServerCreated = void 0;
class ServerCreated {
    constructor(data) {
        this.id = data.id;
        this.name = data.name;
        this.description = data.description;
        this.owner = data.owner;
    }
}
exports.ServerCreated = ServerCreated;
ServerCreated.exchange = "server";
ServerCreated.routingKey = "server.created";
class ServerUpdated {
    constructor(data) {
        this.id = data.id;
        this.name = data.name;
        this.description = data.description;
    }
}
exports.ServerUpdated = ServerUpdated;
ServerUpdated.exchange = "server";
ServerUpdated.routingKey = "server.updated";
class ServerDeleted {
    constructor(data) {
        this.id = data.id;
    }
}
exports.ServerDeleted = ServerDeleted;
ServerDeleted.exchange = "server";
ServerDeleted.routingKey = "server.deleted";
class UserJoinedServer {
    constructor(data) {
        this.serverId = data.serverId;
        this.username = data.username;
    }
}
exports.UserJoinedServer = UserJoinedServer;
UserJoinedServer.exchange = "server";
UserJoinedServer.routingKey = "user.joined";
class UserLeftServer {
    constructor(data) {
        this.serverId = data.serverId;
        this.username = data.username;
    }
}
exports.UserLeftServer = UserLeftServer;
UserLeftServer.exchange = "server";
UserLeftServer.routingKey = "user.left";
class UserKickedFromServer {
    constructor(data) {
        this.serverId = data.serverId;
        this.username = data.username;
    }
}
exports.UserKickedFromServer = UserKickedFromServer;
UserKickedFromServer.exchange = "server";
UserKickedFromServer.routingKey = "user.kicked";
