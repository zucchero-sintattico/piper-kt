"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.Route = void 0;
const validate_1 = require("../api/validate");
const errors_1 = require("../api/errors");
class Route {
    constructor(route) {
        this.method = route.method;
        this.path = route.path;
        this.schema = route.schema;
        this.middlewares = route.middlewares;
        this.handler = route.handler;
        this.exceptions = route.exceptions;
    }
    attachToRouter(router) {
        router[this.method](this.path, (req, res, next) => {
            res.sendResponse = (response) => {
                response.send(res);
            };
            next();
        }, ...(this.middlewares || []), (0, validate_1.Validate)(this.schema), (req, res) => __awaiter(this, void 0, void 0, function* () {
            try {
                yield this.handler(req, res);
            }
            catch (e) {
                if (this.exceptions) {
                    const exception = this.exceptions.find((exception) => {
                        return e instanceof exception.exception;
                    });
                    if (exception) {
                        exception.onException(e, req, res);
                    }
                    else {
                        const response = new errors_1.InternalServerError(e);
                        response.send(res);
                    }
                }
                else {
                    const response = new errors_1.InternalServerError(e);
                    response.send(res);
                }
            }
        }));
    }
}
exports.Route = Route;
