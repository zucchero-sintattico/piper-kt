"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UpdateDescriptionApi = exports.UpdatePhotoApi = void 0;
const response_1 = require("../response");
/* eslint-disable @typescript-eslint/no-namespace */
/**
 * Update photo endpoint
 */
var UpdatePhotoApi;
(function (UpdatePhotoApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {},
            Body: {
                photo: "Photo",
            },
        };
    })(Request = UpdatePhotoApi.Request || (UpdatePhotoApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Photo updated successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = UpdatePhotoApi.Responses || (UpdatePhotoApi.Responses = {}));
    let Errors;
    (function (Errors) {
        class InvalidPhoto extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 400;
                this.message = "Invalid photo";
            }
        }
        Errors.InvalidPhoto = InvalidPhoto;
    })(Errors = UpdatePhotoApi.Errors || (UpdatePhotoApi.Errors = {}));
})(UpdatePhotoApi || (exports.UpdatePhotoApi = UpdatePhotoApi = {}));
/**
 * Update description endpoint
 */
var UpdateDescriptionApi;
(function (UpdateDescriptionApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {},
            Body: {
                description: "string",
            },
        };
    })(Request = UpdateDescriptionApi.Request || (UpdateDescriptionApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor() {
                super(...arguments);
                this.statusCode = 200;
                this.message = "Description updated successfully";
            }
        }
        Responses.Success = Success;
    })(Responses = UpdateDescriptionApi.Responses || (UpdateDescriptionApi.Responses = {}));
})(UpdateDescriptionApi || (exports.UpdateDescriptionApi = UpdateDescriptionApi = {}));
