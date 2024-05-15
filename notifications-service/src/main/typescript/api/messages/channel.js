"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.SendMessageInChannelApi = exports.GetChannelMessagesApi = void 0;
const response_1 = require("../response");
/* eslint-disable @typescript-eslint/no-namespace */
var GetChannelMessagesApi;
(function (GetChannelMessagesApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
                channelId: "string",
            },
            Body: {},
            Query: {
                from: "number",
                limit: "number",
            },
        };
    })(Request = GetChannelMessagesApi.Request || (GetChannelMessagesApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(messages) {
                super();
                this.messages = messages;
                this.statusCode = 200;
                this.message = "Messages retrieved successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = GetChannelMessagesApi.Responses || (GetChannelMessagesApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class ChannelNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Channel not found";
            }
        }
        Errors.ChannelNotFound = ChannelNotFound;
        class ServerNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Server not found";
            }
        }
        Errors.ServerNotFound = ServerNotFound;
        class UserNotAuthorized extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 403;
                this.error = "User not authorized";
            }
        }
        Errors.UserNotAuthorized = UserNotAuthorized;
    })(Errors = GetChannelMessagesApi.Errors || (GetChannelMessagesApi.Errors = {}));
})(GetChannelMessagesApi || (exports.GetChannelMessagesApi = GetChannelMessagesApi = {}));
var SendMessageInChannelApi;
(function (SendMessageInChannelApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
                channelId: "string",
            },
            Body: {
                content: "string",
            },
        };
    })(Request = SendMessageInChannelApi.Request || (SendMessageInChannelApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Message sent successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = SendMessageInChannelApi.Responses || (SendMessageInChannelApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class ChannelNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Channel not found";
            }
        }
        Errors.ChannelNotFound = ChannelNotFound;
        class ServerNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Server not found";
            }
        }
        Errors.ServerNotFound = ServerNotFound;
        class UserNotAuthorized extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 403;
                this.error = "User not authorized";
            }
        }
        Errors.UserNotAuthorized = UserNotAuthorized;
    })(Errors = SendMessageInChannelApi.Errors || (SendMessageInChannelApi.Errors = {}));
})(SendMessageInChannelApi || (exports.SendMessageInChannelApi = SendMessageInChannelApi = {}));
