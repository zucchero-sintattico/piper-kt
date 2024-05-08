"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Users = exports.UserSchema = void 0;
const mongoose_1 = require("mongoose");
exports.UserSchema = new mongoose_1.Schema({
    username: {
        type: String,
        required: true,
        unique: true,
        key: true,
    },
    friends: [
        {
            type: String,
            default: [],
        },
    ],
});
exports.Users = (0, mongoose_1.model)("User", exports.UserSchema);
