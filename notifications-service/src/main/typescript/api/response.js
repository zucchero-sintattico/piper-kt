"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ErrorResponse = exports.Response = void 0;
class Response {
    send(res) {
        res === null || res === void 0 ? void 0 : res.status(this.statusCode).json(this);
    }
}
exports.Response = Response;
class ErrorResponse extends Response {
}
exports.ErrorResponse = ErrorResponse;
