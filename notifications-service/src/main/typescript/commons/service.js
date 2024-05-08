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
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.DefaultMiddlewares = exports.EmptyRouter = exports.Microservice = void 0;
const express_1 = __importDefault(require("express"));
const http_1 = __importDefault(require("http"));
const router_1 = require("./healthcheck/router");
const mongoose_1 = require("./utils/mongoose");
const rabbit_mq_1 = require("./utils/rabbit-mq");
const service_events_1 = require("./events/service-events");
const cookie_parser_1 = __importDefault(require("cookie-parser"));
const mongoose_2 = __importDefault(require("mongoose"));
class ExpressService {
    constructor(port, config) {
        this.port = port;
        this.middlewares = config.middlewares;
        this.serviceRouter = config.serviceRouter;
        this.otherServices = config.serverBasedServices;
    }
    start() {
        return __awaiter(this, arguments, void 0, function* (onStarted = () => { }) {
            return new Promise((resolve) => {
                var _a;
                const app = (0, express_1.default)();
                app.use(...this.middlewares);
                app.use("/", router_1.healthCheckRouter);
                app.use("/", this.serviceRouter);
                this.server = http_1.default.createServer(app);
                (_a = this.otherServices) === null || _a === void 0 ? void 0 : _a.forEach((service) => {
                    service(this.server);
                });
                this.server.listen(this.port, () => {
                    onStarted();
                    resolve();
                });
            });
        });
    }
    stop() {
        return __awaiter(this, void 0, void 0, function* () {
            return new Promise((resolve) => {
                if (this.server) {
                    this.server.close(() => {
                        resolve();
                    });
                }
                else {
                    resolve();
                }
            });
        });
    }
}
class Microservice {
    constructor(configuration) {
        this.configuration = configuration;
    }
    getServer() {
        return this.app.server;
    }
    clearDatabase() {
        return __awaiter(this, void 0, void 0, function* () {
            yield mongoose_1.MongooseUtils.clear(this.configuration.mongoose || mongoose_2.default);
        });
    }
    start() {
        return __awaiter(this, void 0, void 0, function* () {
            yield mongoose_1.MongooseUtils.initialize(this.configuration.mongoose || mongoose_2.default, this.configuration.mongoUri);
            yield rabbit_mq_1.RabbitMQ.initialize(this.configuration.amqpUri);
            if (this.configuration.eventsConfiguration) {
                yield service_events_1.ServiceEvents.initialize(rabbit_mq_1.RabbitMQ.getInstance(), this.configuration.eventsConfiguration);
            }
            for (const service of this.configuration.otherServices || []) {
                yield service.start();
            }
            this.app = new ExpressService(this.configuration.port, this.configuration.expressConfiguration);
            yield this.app.start(() => {
                console.log(`Started on port: ${this.configuration.port}`);
            });
        });
    }
    stop() {
        return __awaiter(this, void 0, void 0, function* () {
            var _a;
            yield ((_a = this.app) === null || _a === void 0 ? void 0 : _a.stop());
            yield mongoose_1.MongooseUtils.close(this.configuration.mongoose || mongoose_2.default);
            yield rabbit_mq_1.RabbitMQ.disconnect();
            for (const service of this.configuration.otherServices || []) {
                yield service.stop();
            }
        });
    }
}
exports.Microservice = Microservice;
exports.EmptyRouter = express_1.default.Router();
exports.DefaultMiddlewares = [
    express_1.default.json(),
    express_1.default.urlencoded({ extended: true }),
    (0, cookie_parser_1.default)(),
];
