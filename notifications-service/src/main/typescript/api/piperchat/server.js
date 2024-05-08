"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CreateServerApi = exports.GetServersApi = exports.DeleteServerApi = exports.UpdateServerApi = exports.GetServerApi = exports.LeaveServerApi = exports.JoinServerApi = exports.GetServerParticipantsApi = exports.KickUserFromServerApi = void 0;
const response_1 = require("../response");
/* eslint-disable @typescript-eslint/no-namespace */
var KickUserFromServerApi;
(function (KickUserFromServerApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
                username: "string",
            },
            Body: {},
        };
    })(Request = KickUserFromServerApi.Request || (KickUserFromServerApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "User kicked successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = KickUserFromServerApi.Responses || (KickUserFromServerApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class ServerNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Server or user not found";
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
        class OwnerCannotLeave extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 405;
                this.error = "Owner cannot leave the server";
            }
        }
        Errors.OwnerCannotLeave = OwnerCannotLeave;
    })(Errors = KickUserFromServerApi.Errors || (KickUserFromServerApi.Errors = {}));
})(KickUserFromServerApi || (exports.KickUserFromServerApi = KickUserFromServerApi = {}));
var GetServerParticipantsApi;
(function (GetServerParticipantsApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
            },
            Body: {},
        };
    })(Request = GetServerParticipantsApi.Request || (GetServerParticipantsApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(participants) {
                super();
                this.participants = participants;
                this.statusCode = 200;
            }
        }
        Responses.Success = Success;
    })(Responses = GetServerParticipantsApi.Responses || (GetServerParticipantsApi.Responses = {}));
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
    })(Errors = GetServerParticipantsApi.Errors || (GetServerParticipantsApi.Errors = {}));
})(GetServerParticipantsApi || (exports.GetServerParticipantsApi = GetServerParticipantsApi = {}));
var JoinServerApi;
(function (JoinServerApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
            },
            Body: {},
        };
    })(Request = JoinServerApi.Request || (JoinServerApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Server joined successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = JoinServerApi.Responses || (JoinServerApi.Responses = {}));
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
        class UserAlreadyJoined extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 403;
                this.error = "User already joined";
            }
        }
        Errors.UserAlreadyJoined = UserAlreadyJoined;
    })(Errors = JoinServerApi.Errors || (JoinServerApi.Errors = {}));
})(JoinServerApi || (exports.JoinServerApi = JoinServerApi = {}));
var LeaveServerApi;
(function (LeaveServerApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
            },
            Body: {},
        };
    })(Request = LeaveServerApi.Request || (LeaveServerApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Server left successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = LeaveServerApi.Responses || (LeaveServerApi.Responses = {}));
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
        class UserNotInServer extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 403;
                this.error = "User not in server";
            }
        }
        Errors.UserNotInServer = UserNotInServer;
        class OwnerCannotLeave extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 422;
                this.error = "Owner cannot leave the server";
            }
        }
        Errors.OwnerCannotLeave = OwnerCannotLeave;
    })(Errors = LeaveServerApi.Errors || (LeaveServerApi.Errors = {}));
})(LeaveServerApi || (exports.LeaveServerApi = LeaveServerApi = {}));
var GetServerApi;
(function (GetServerApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
            },
            Body: {},
        };
    })(Request = GetServerApi.Request || (GetServerApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(server) {
                super();
                this.server = server;
                this.statusCode = 200;
            }
        }
        Responses.Success = Success;
    })(Responses = GetServerApi.Responses || (GetServerApi.Responses = {}));
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
    })(Errors = GetServerApi.Errors || (GetServerApi.Errors = {}));
})(GetServerApi || (exports.GetServerApi = GetServerApi = {}));
var UpdateServerApi;
(function (UpdateServerApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
            },
            Body: {
                name: "string?",
                description: "string?",
            },
        };
    })(Request = UpdateServerApi.Request || (UpdateServerApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Server updated successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = UpdateServerApi.Responses || (UpdateServerApi.Responses = {}));
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
        class NameOrDescriptionRequired extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 422;
                this.error = "Name or description required";
            }
        }
        Errors.NameOrDescriptionRequired = NameOrDescriptionRequired;
    })(Errors = UpdateServerApi.Errors || (UpdateServerApi.Errors = {}));
})(UpdateServerApi || (exports.UpdateServerApi = UpdateServerApi = {}));
var DeleteServerApi;
(function (DeleteServerApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                serverId: "string",
            },
            Body: {},
        };
    })(Request = DeleteServerApi.Request || (DeleteServerApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Server deleted successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = DeleteServerApi.Responses || (DeleteServerApi.Responses = {}));
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
    })(Errors = DeleteServerApi.Errors || (DeleteServerApi.Errors = {}));
})(DeleteServerApi || (exports.DeleteServerApi = DeleteServerApi = {}));
var GetServersApi;
(function (GetServersApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {},
            Body: {},
        };
    })(Request = GetServersApi.Request || (GetServersApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(servers) {
                super();
                this.servers = servers;
                this.statusCode = 200;
            }
        }
        Responses.Success = Success;
    })(Responses = GetServersApi.Responses || (GetServersApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class UserNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "User not found";
            }
        }
        Errors.UserNotFound = UserNotFound;
    })(Errors = GetServersApi.Errors || (GetServersApi.Errors = {}));
})(GetServersApi || (exports.GetServersApi = GetServersApi = {}));
var CreateServerApi;
(function (CreateServerApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {},
            Body: {
                name: "string",
                description: "string",
            },
        };
    })(Request = CreateServerApi.Request || (CreateServerApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(serverId) {
                super();
                this.statusCode = 200;
                this.message = "Server created successfully";
                this.serverId = serverId;
            }
        }
        Responses.Success = Success;
    })(Responses = CreateServerApi.Responses || (CreateServerApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class NameOrDescriptionRequired extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 422;
                this.error = "Name or description required";
            }
        }
        Errors.NameOrDescriptionRequired = NameOrDescriptionRequired;
    })(Errors = CreateServerApi.Errors || (CreateServerApi.Errors = {}));
})(CreateServerApi || (exports.CreateServerApi = CreateServerApi = {}));
