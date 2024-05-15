"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UsersStatus = void 0;
const mongoose_1 = require("mongoose");
const UserStatusSchema = new mongoose_1.Schema({
    username: {
        type: String,
        required: true,
        unique: true,
        key: true,
    },
    status: {
        type: String,
    },
    lastActive: {
        type: Date,
    },
});
const UsersStatus = (0, mongoose_1.model)("UserStatus", UserStatusSchema);
exports.UsersStatus = UsersStatus;
