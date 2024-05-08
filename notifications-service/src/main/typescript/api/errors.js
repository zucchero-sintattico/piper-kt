"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.JwtTokenMissingOrInvalid = exports.BadRequest = exports.InternalServerError = void 0;
const response_1 = require("./response");
class InternalServerError extends response_1.ErrorResponse {
    constructor(e = "") {
        super();
        this.statusCode = 500;
        this.error = "Internal Server Error";
        this.errorMessage = e;
    }
}
exports.InternalServerError = InternalServerError;
class BadRequest extends response_1.ErrorResponse {
    constructor(params, body, query) {
        super();
        this.statusCode = 400;
        this.error = "Bad Request";
        this.missingParams = params;
        this.missingBody = body;
        this.missingQuery = query;
    }
}
exports.BadRequest = BadRequest;
class JwtTokenMissingOrInvalid extends response_1.ErrorResponse {
    constructor() {
        super(...arguments);
        this.statusCode = 401;
        this.error = "JWT Token Missing or Invalid";
    }
}
exports.JwtTokenMissingOrInvalid = JwtTokenMissingOrInvalid;
