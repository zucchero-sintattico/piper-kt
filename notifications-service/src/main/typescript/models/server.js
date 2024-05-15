"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Servers = exports.ServerSchema = exports.ChannelSchema = void 0;
const mongoose_1 = require("mongoose");
exports.ChannelSchema = new mongoose_1.Schema({
    channelType: {
        type: String,
        required: true,
    },
});
exports.ChannelSchema.set("toJSON", {
    virtuals: true,
});
exports.ServerSchema = new mongoose_1.Schema({
    owner: {
        type: String,
        required: true,
    },
    participants: {
        type: [String],
        required: true,
    },
    channels: {
        type: [exports.ChannelSchema],
        required: true,
        default: [],
    },
});
exports.ServerSchema.set("toJSON", {
    virtuals: true,
});
exports.Servers = (0, mongoose_1.model)("Server", exports.ServerSchema);
