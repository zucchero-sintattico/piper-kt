"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.SendFriendRequestApi = exports.GetFriendsRequestsApi = exports.GetFriendsApi = void 0;
const response_1 = require("../response");
const schema_1 = require("../schema");
/* eslint-disable @typescript-eslint/no-namespace */
var GetFriendsApi;
(function (GetFriendsApi) {
    let Request;
    (function (Request) {
        Request.Schema = schema_1.EmptySchema;
    })(Request = GetFriendsApi.Request || (GetFriendsApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(friends) {
                super();
                this.statusCode = 200;
                this.friends = friends;
            }
        }
        Responses.Success = Success;
    })(Responses = GetFriendsApi.Responses || (GetFriendsApi.Responses = {}));
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
    })(Errors = GetFriendsApi.Errors || (GetFriendsApi.Errors = {}));
})(GetFriendsApi || (exports.GetFriendsApi = GetFriendsApi = {}));
var GetFriendsRequestsApi;
(function (GetFriendsRequestsApi) {
    let Request;
    (function (Request) {
        Request.Schema = schema_1.EmptySchema;
    })(Request = GetFriendsRequestsApi.Request || (GetFriendsRequestsApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(requests) {
                super();
                this.statusCode = 200;
                this.requests = requests;
            }
        }
        Responses.Success = Success;
    })(Responses = GetFriendsRequestsApi.Responses || (GetFriendsRequestsApi.Responses = {}));
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
    })(Errors = GetFriendsRequestsApi.Errors || (GetFriendsRequestsApi.Errors = {}));
})(GetFriendsRequestsApi || (exports.GetFriendsRequestsApi = GetFriendsRequestsApi = {}));
var SendFriendRequestApi;
(function (SendFriendRequestApi) {
    let Request;
    (function (Request) {
        let FriendRequestAction;
        (function (FriendRequestAction) {
            FriendRequestAction["send"] = "send";
            FriendRequestAction["accept"] = "accept";
            FriendRequestAction["deny"] = "deny";
        })(FriendRequestAction = Request.FriendRequestAction || (Request.FriendRequestAction = {}));
        Request.Schema = {
            Params: {},
            Body: {
                to: "string",
                action: "string",
            },
        };
    })(Request = SendFriendRequestApi.Request || (SendFriendRequestApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Friend request sent";
            }
        }
        Responses.Success = Success;
        class FriendRequestAccepted extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Friend request accepted";
            }
        }
        Responses.FriendRequestAccepted = FriendRequestAccepted;
        class FriendRequestDenied extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Friend request denied";
            }
        }
        Responses.FriendRequestDenied = FriendRequestDenied;
    })(Responses = SendFriendRequestApi.Responses || (SendFriendRequestApi.Responses = {}));
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
        class InvalidAction extends response_1.ErrorResponse {
            constructor(action) {
                super();
                this.statusCode = 400;
                this.error = `Invalid 'action' parameter in body: '${action}'`;
            }
        }
        Errors.InvalidAction = InvalidAction;
        class FriendRequestAlreadySent extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 409;
                this.error = "Friend request already sent";
            }
        }
        Errors.FriendRequestAlreadySent = FriendRequestAlreadySent;
        class FriendRequestNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Friend request not found";
            }
        }
        Errors.FriendRequestNotFound = FriendRequestNotFound;
        class CannotSendFriendRequestToYourself extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 400;
                this.error = "Cannot send a friend request to yourself";
            }
        }
        Errors.CannotSendFriendRequestToYourself = CannotSendFriendRequestToYourself;
    })(Errors = SendFriendRequestApi.Errors || (SendFriendRequestApi.Errors = {}));
})(SendFriendRequestApi || (exports.SendFriendRequestApi = SendFriendRequestApi = {}));
