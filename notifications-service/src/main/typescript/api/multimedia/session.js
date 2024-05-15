"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.GetUsersInSession = exports.GetDirectSessionIdApi = exports.GetChannelSessionIdApi = void 0;
const response_1 = require("../response");
/* eslint-disable @typescript-eslint/no-namespace */
var GetChannelSessionIdApi;
(function (GetChannelSessionIdApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
                channelId: "string",
            },
            Body: {},
        };
    })(Request = GetChannelSessionIdApi.Request || (GetChannelSessionIdApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(data) {
                super();
                this.statusCode = 200;
                this.message = "Session id retrieved successfully";
                this.sessionId = data.sessionId;
            }
        }
        Responses.Success = Success;
    })(Responses = GetChannelSessionIdApi.Responses || (GetChannelSessionIdApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class ChannelNotFound extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.message = "Channel not found";
            }
        }
        Errors.ChannelNotFound = ChannelNotFound;
        class UserNotAuthorized extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 403;
                this.message = "User not authorized";
            }
        }
        Errors.UserNotAuthorized = UserNotAuthorized;
    })(Errors = GetChannelSessionIdApi.Errors || (GetChannelSessionIdApi.Errors = {}));
})(GetChannelSessionIdApi || (exports.GetChannelSessionIdApi = GetChannelSessionIdApi = {}));
var GetDirectSessionIdApi;
(function (GetDirectSessionIdApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                username: "string",
            },
            Body: {},
        };
    })(Request = GetDirectSessionIdApi.Request || (GetDirectSessionIdApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(data) {
                super();
                this.statusCode = 200;
                this.message = "Session id retrieved successfully";
                this.sessionId = data.sessionId;
            }
        }
        Responses.Success = Success;
    })(Responses = GetDirectSessionIdApi.Responses || (GetDirectSessionIdApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class FriendshipNotFound extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.message = "Friendship not found";
            }
        }
        Errors.FriendshipNotFound = FriendshipNotFound;
    })(Errors = GetDirectSessionIdApi.Errors || (GetDirectSessionIdApi.Errors = {}));
})(GetDirectSessionIdApi || (exports.GetDirectSessionIdApi = GetDirectSessionIdApi = {}));
var GetUsersInSession;
(function (GetUsersInSession) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                sessionId: "string",
            },
            Body: {},
        };
    })(Request = GetUsersInSession.Request || (GetUsersInSession.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(data) {
                super();
                this.statusCode = 200;
                this.message = "Users retrieved successfully";
                this.users = data.users;
            }
        }
        Responses.Success = Success;
    })(Responses = GetUsersInSession.Responses || (GetUsersInSession.Responses = {}));
    let Errors;
    (function (Errors) {
        class SessionNotFound extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.message = "Session not found";
            }
        }
        Errors.SessionNotFound = SessionNotFound;
    })(Errors = GetUsersInSession.Errors || (GetUsersInSession.Errors = {}));
})(GetUsersInSession || (exports.GetUsersInSession = GetUsersInSession = {}));
