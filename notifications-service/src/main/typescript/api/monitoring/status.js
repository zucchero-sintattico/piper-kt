"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.GetServicesStatusApi = void 0;
const response_1 = require("../response");
/* eslint-disable @typescript-eslint/no-namespace */
var GetServicesStatusApi;
(function (GetServicesStatusApi) {
    let Request;
    (function (Request) {
        Request.Schema = {
            Params: {},
            Body: {},
        };
    })(Request = GetServicesStatusApi.Request || (GetServicesStatusApi.Request = {}));
    let Responses;
    (function (Responses) {
        class Success extends response_1.Response {
            constructor(services) {
                super();
                this.statusCode = 200;
                this.services = services;
            }
        }
        Responses.Success = Success;
    })(Responses = GetServicesStatusApi.Responses || (GetServicesStatusApi.Responses = {}));
})(GetServicesStatusApi || (exports.GetServicesStatusApi = GetServicesStatusApi = {}));
