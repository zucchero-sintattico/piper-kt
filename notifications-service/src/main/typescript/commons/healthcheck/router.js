"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.healthCheckRouter = void 0;
const express_1 = require("express");
const healthCheckRouter = (0, express_1.Router)();
exports.healthCheckRouter = healthCheckRouter;
healthCheckRouter.get("/health", (req, res) => {
    res.status(200).send("OK");
});
