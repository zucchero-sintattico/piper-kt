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
exports.userStatusRouter = void 0;
const express_1 = require("express");
const user_status_controller_1 = require("../../controllers/user-status-controller");
const user_1 = require("../../api/users/user");
const route_1 = require("../../commons/route");
const userStatusController = new user_status_controller_1.UserStatusControllerImpl();
const GetUserStatusApiRoute = new route_1.Route({
    method: "get",
    path: "/:username/status",
    schema: user_1.GetUserStatusApi.Request.Schema,
    handler: (req, res) => __awaiter(void 0, void 0, void 0, function* () {
        var _a;
        const status = yield userStatusController.getStatus(req.params.username);
        const response = new user_1.GetUserStatusApi.Responses.Success({
            online: status.status === "online",
            lastActive: (_a = status.lastActive) !== null && _a !== void 0 ? _a : new Date(0),
        });
        res.sendResponse(response);
    }),
    exceptions: [
        {
            exception: user_status_controller_1.UserNotFoundException,
            onException(e, req, res) {
                res.sendResponse(new user_1.GetUserStatusApi.Errors.UserNotFound());
            },
        },
    ],
});
exports.userStatusRouter = (0, express_1.Router)({
    strict: true,
    mergeParams: true,
});
GetUserStatusApiRoute.attachToRouter(exports.userStatusRouter);
