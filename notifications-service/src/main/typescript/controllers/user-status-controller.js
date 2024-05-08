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
exports.UserStatusControllerImpl = exports.UserNotFoundException = void 0;
const user_status_repository_1 = require("../repositories/user-status-repository");
class UserNotFoundException extends Error {
}
exports.UserNotFoundException = UserNotFoundException;
class UserStatusControllerImpl {
    constructor() {
        this.userRepository = new user_status_repository_1.UserStatusRepositoryImpl();
    }
    getStatus(username) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                return yield this.userRepository.getUser(username);
            }
            catch (e) {
                throw new UserNotFoundException();
            }
        });
    }
}
exports.UserStatusControllerImpl = UserStatusControllerImpl;
