"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.DeleteChannelApi = exports.UpdateChannelApi = exports.CreateChannelApi = exports.GetChannelByIdApi = exports.GetChannelsApi = void 0;
const response_1 = require("../response");
/* eslint-disable @typescript-eslint/no-namespace */
var GetChannelsApi;
(function (GetChannelsApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
            },
            Body: {},
        };
    })(Request = GetChannelsApi.Request || (GetChannelsApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(channels) {
                super();
                this.channels = channels;
                this.statusCode = 200;
                this.message = "Channels retrieved successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = GetChannelsApi.Responses || (GetChannelsApi.Responses = {}));
    let Errors;
    (function (Errors) {
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
    })(Errors = GetChannelsApi.Errors || (GetChannelsApi.Errors = {}));
})(GetChannelsApi || (exports.GetChannelsApi = GetChannelsApi = {}));
var GetChannelByIdApi;
(function (GetChannelByIdApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
                channelId: "string",
            },
            Body: {},
        };
    })(Request = GetChannelByIdApi.Request || (GetChannelByIdApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(channel) {
                super();
                this.channel = channel;
                this.statusCode = 200;
                this.message = "Channels retrieved successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = GetChannelByIdApi.Responses || (GetChannelByIdApi.Responses = {}));
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
        class UserNotAuthorized extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 403;
                this.error = "User not authorized";
            }
        }
        Errors.UserNotAuthorized = UserNotAuthorized;
        class ServerNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Server not found";
            }
        }
        Errors.ServerNotFound = ServerNotFound;
    })(Errors = GetChannelByIdApi.Errors || (GetChannelByIdApi.Errors = {}));
})(GetChannelByIdApi || (exports.GetChannelByIdApi = GetChannelByIdApi = {}));
var CreateChannelApi;
(function (CreateChannelApi) {
    let ChannelType;
    (function (ChannelType) {
        ChannelType["Messages"] = "messages";
        ChannelType["Multimedia"] = "multimedia";
    })(ChannelType = CreateChannelApi.ChannelType || (CreateChannelApi.ChannelType = {}));
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
            },
            Body: {
                name: "string",
                channelType: "string",
                description: "string?",
            },
        };
    })(Request = CreateChannelApi.Request || (CreateChannelApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(channel) {
                super();
                this.statusCode = 200;
                this.message = "Channel created successfully";
                this.channel = channel;
            }
        }
        Responses.Success = Success;
    })(Responses = CreateChannelApi.Responses || (CreateChannelApi.Responses = {}));
    let Errors;
    (function (Errors) {
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
        class ChannelAlreadyExists extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 409;
                this.error = "Channel already exists";
            }
        }
        Errors.ChannelAlreadyExists = ChannelAlreadyExists;
        class InvalidChannelType extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 400;
                this.error = "Invalid channel type";
            }
        }
        Errors.InvalidChannelType = InvalidChannelType;
    })(Errors = CreateChannelApi.Errors || (CreateChannelApi.Errors = {}));
})(CreateChannelApi || (exports.CreateChannelApi = CreateChannelApi = {}));
var UpdateChannelApi;
(function (UpdateChannelApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
                channelId: "string",
            },
            Body: {
                name: "string?",
                description: "string?",
            },
        };
    })(Request = UpdateChannelApi.Request || (UpdateChannelApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Channel updated successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = UpdateChannelApi.Responses || (UpdateChannelApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class ServerNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Server not found";
            }
        }
        Errors.ServerNotFound = ServerNotFound;
        class ChannelNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Channel not found";
            }
        }
        Errors.ChannelNotFound = ChannelNotFound;
        class UserNotAuthorized extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 403;
                this.error = "User not authorized";
            }
        }
        Errors.UserNotAuthorized = UserNotAuthorized;
        class ChannelAlreadyExists extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 409;
                this.error = "Channel already exists";
            }
        }
        Errors.ChannelAlreadyExists = ChannelAlreadyExists;
    })(Errors = UpdateChannelApi.Errors || (UpdateChannelApi.Errors = {}));
})(UpdateChannelApi || (exports.UpdateChannelApi = UpdateChannelApi = {}));
var DeleteChannelApi;
(function (DeleteChannelApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
                channelId: "string",
            },
            Body: {},
        };
    })(Request = DeleteChannelApi.Request || (DeleteChannelApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Channel deleted successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = DeleteChannelApi.Responses || (DeleteChannelApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class ServerNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Server not found";
            }
        }
        Errors.ServerNotFound = ServerNotFound;
        class ChannelNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Channel not found";
            }
        }
        Errors.ChannelNotFound = ChannelNotFound;
        class UserNotAuthorized extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 403;
                this.error = "User not authorized";
            }
        }
        Errors.UserNotAuthorized = UserNotAuthorized;
    })(Errors = DeleteChannelApi.Errors || (DeleteChannelApi.Errors = {}));
})(DeleteChannelApi || (exports.DeleteChannelApi = DeleteChannelApi = {}));
