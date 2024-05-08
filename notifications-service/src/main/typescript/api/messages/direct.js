"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.SendDirectMessageApi = exports.GetDirectMessagesApi = void 0;
const response_1 = require("../response");
/* eslint-disable @typescript-eslint/no-namespace */
var GetDirectMessagesApi;
(function (GetDirectMessagesApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                username: "string",
            },
            Body: {},
            Query: {
                from: "number",
                limit: "number",
            },
        };
    })(Request = GetDirectMessagesApi.Request || (GetDirectMessagesApi.Request = {}));
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
    })(Responses = GetDirectMessagesApi.Responses || (GetDirectMessagesApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class DirectNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Direct not found";
            }
        }
        Errors.DirectNotFound = DirectNotFound;
    })(Errors = GetDirectMessagesApi.Errors || (GetDirectMessagesApi.Errors = {}));
})(GetDirectMessagesApi || (exports.GetDirectMessagesApi = GetDirectMessagesApi = {}));
var SendDirectMessageApi;
(function (SendDirectMessageApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {
                username: "string",
            },
            Body: {
                message: "string",
            },
        };
    })(Request = SendDirectMessageApi.Request || (SendDirectMessageApi.Request = {}));
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
    })(Responses = SendDirectMessageApi.Responses || (SendDirectMessageApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class DirectNotFound extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 404;
                this.error = "Direct not found";
            }
        }
        Errors.DirectNotFound = DirectNotFound;
        class CannotSendDirectMessageToYourself extends response_1.ErrorResponse {
            constructor() {
                super(...arguments);
                this.statusCode = 400;
                this.error = "Cannot send direct message to yourself";
            }
        }
        Errors.CannotSendDirectMessageToYourself = CannotSendDirectMessageToYourself;
    })(Errors = SendDirectMessageApi.Errors || (SendDirectMessageApi.Errors = {}));
})(SendDirectMessageApi || (exports.SendDirectMessageApi = SendDirectMessageApi = {}));
