"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.RefreshTokenApi = exports.LogoutApi = exports.LoginApi = exports.RegisterApi = void 0;
const response_1 = require("../response");
const schema_1 = require("../schema");
/* eslint-disable @typescript-eslint/no-namespace */
/**
 * Register endpoint
 */
var RegisterApi;
(function (RegisterApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {},
            Body: {
                username: "string",
                password: "string",
                email: "string",
                description: "string?",
                photo: "Buffer?",
            },
        };
    })(Request = RegisterApi.Request || (RegisterApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(user) {
                super();
                this.statusCode = 200;
                this.createdUser = user;
            }
        }
        Responses.Success = Success;
    })(Responses = RegisterApi.Responses || (RegisterApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class UserAlreadyExists extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 409;
                this.error = "User already exists";
            }
        }
        Errors.UserAlreadyExists = UserAlreadyExists;
        class EmailAlreadyExists extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 409;
                this.error = "Email already exists";
            }
        }
        Errors.EmailAlreadyExists = EmailAlreadyExists;
    })(Errors = RegisterApi.Errors || (RegisterApi.Errors = {}));
})(RegisterApi || (exports.RegisterApi = RegisterApi = {}));
/**
 * Login endpoint
 */
var LoginApi;
(function (LoginApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {},
            Body: {
                username: "string",
                password: "string",
            },
        };
    })(Request = LoginApi.Request || (LoginApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(jwt) {
                super();
                this.statusCode = 200;
                this.message = "Logged in";
                this.jwt = jwt;
            }
            send(res) {
                res.cookie("access_token", this.jwt, { httpOnly: true });
                super.send(res);
            }
        }
        Responses.Success = Success;
    })(Responses = LoginApi.Responses || (LoginApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class UsernameOrPasswordIncorrect extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 401;
                this.error = "Username or password incorrect";
            }
        }
        Errors.UsernameOrPasswordIncorrect = UsernameOrPasswordIncorrect;
    })(Errors = LoginApi.Errors || (LoginApi.Errors = {}));
})(LoginApi || (exports.LoginApi = LoginApi = {}));
/**
 * Logout endpoint
 */
var LogoutApi;
(function (LogoutApi) {
    let Request;
    (function (Request) {
        Request.Schema = schema_1.EmptySchema;
    })(Request = LogoutApi.Request || (LogoutApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Logged out";
            }
            send(res) {
                res.clearCookie("access_token");
                super.send(res);
            }
        }
        Responses.Success = Success;
    })(Responses = LogoutApi.Responses || (LogoutApi.Responses = {}));
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
    })(Errors = LogoutApi.Errors || (LogoutApi.Errors = {}));
})(LogoutApi || (exports.LogoutApi = LogoutApi = {}));
/**
 * Refresh token endpoint
 */
var RefreshTokenApi;
(function (RefreshTokenApi) {
    let Request;
    (function (Request) {
        Request.Schema = schema_1.EmptySchema;
    })(Request = RefreshTokenApi.Request || (RefreshTokenApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(jwt) {
                super();
                this.statusCode = 200;
                this.message = "Refreshed token";
                this.jwt = jwt;
            }
            send(res) {
                res.cookie("jwt", this.jwt, { httpOnly: true });
                super.send(res);
            }
        }
        Responses.Success = Success;
    })(Responses = RefreshTokenApi.Responses || (RefreshTokenApi.Responses = {}));
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
        class InvalidRefreshToken extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 401;
                this.error = "Refresh token is missing or invalid";
            }
        }
        Errors.InvalidRefreshToken = InvalidRefreshToken;
    })(Errors = RefreshTokenApi.Errors || (RefreshTokenApi.Errors = {}));
})(RefreshTokenApi || (exports.RefreshTokenApi = RefreshTokenApi = {}));
