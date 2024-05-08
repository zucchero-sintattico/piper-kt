"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.GetUserDescriptionApi = exports.GetUserPhotoApi = exports.GetUserStatusApi = exports.WhoamiApi = void 0;
/* eslint-disable @typescript-eslint/no-namespace */
const response_1 = require("../response");
const schema_1 = require("../schema");
/**
 * Whoami endpoint
 */
var WhoamiApi;
(function (WhoamiApi) {
    let Request;
    (function (Request) {
        Request.Schema = schema_1.EmptySchema;
    })(Request = WhoamiApi.Request || (WhoamiApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(user) {
                super();
                this.statusCode = 200;
                this.user = user;
            }
        }
        Responses.Success = Success;
    })(Responses = WhoamiApi.Responses || (WhoamiApi.Responses = {}));
})(WhoamiApi || (exports.WhoamiApi = WhoamiApi = {}));
/**
 * Get user status
 */
var GetUserStatusApi;
(function (GetUserStatusApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                username: "string",
            },
            Body: {},
        };
    })(Request = GetUserStatusApi.Request || (GetUserStatusApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(status) {
                super();
                this.statusCode = 200;
                this.status = status;
            }
        }
        Responses.Success = Success;
    })(Responses = GetUserStatusApi.Responses || (GetUserStatusApi.Responses = {}));
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
    })(Errors = GetUserStatusApi.Errors || (GetUserStatusApi.Errors = {}));
})(GetUserStatusApi || (exports.GetUserStatusApi = GetUserStatusApi = {}));
/**
 * Get user photo
 */
var GetUserPhotoApi;
(function (GetUserPhotoApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                username: "string",
            },
            Body: {},
        };
    })(Request = GetUserPhotoApi.Request || (GetUserPhotoApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(photo) {
                super();
                this.statusCode = 200;
                this.photo = photo;
            }
        }
        Responses.Success = Success;
    })(Responses = GetUserPhotoApi.Responses || (GetUserPhotoApi.Responses = {}));
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
    })(Errors = GetUserPhotoApi.Errors || (GetUserPhotoApi.Errors = {}));
})(GetUserPhotoApi || (exports.GetUserPhotoApi = GetUserPhotoApi = {}));
/**
 * Get user description
 */
var GetUserDescriptionApi;
(function (GetUserDescriptionApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                username: "string",
            },
            Body: {},
        };
    })(Request = GetUserDescriptionApi.Request || (GetUserDescriptionApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(description) {
                super();
                this.statusCode = 200;
                this.description = description;
            }
        }
        Responses.Success = Success;
    })(Responses = GetUserDescriptionApi.Responses || (GetUserDescriptionApi.Responses = {}));
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
    })(Errors = GetUserDescriptionApi.Errors || (GetUserDescriptionApi.Errors = {}));
})(GetUserDescriptionApi || (exports.GetUserDescriptionApi = GetUserDescriptionApi = {}));
