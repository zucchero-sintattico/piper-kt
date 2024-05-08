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
exports.RabbitMQ = void 0;
const amqplib_1 = __importDefault(require("amqplib"));
class RabbitMQ {
    static initialize(connectionUri) {
        return __awaiter(this, void 0, void 0, function* () {
            if (!RabbitMQ.instance) {
                RabbitMQ.instance = new RabbitMQ(connectionUri);
                yield RabbitMQ.instance.connect();
                console.log("Connected to RabbitMQ");
            }
        });
    }
    static disconnect() {
        return __awaiter(this, void 0, void 0, function* () {
            var _a;
            if (RabbitMQ.instance) {
                yield ((_a = RabbitMQ.instance.connection) === null || _a === void 0 ? void 0 : _a.close());
            }
        });
    }
    static getInstance() {
        if (!RabbitMQ.instance) {
            console.log(RabbitMQ.instance);
            throw new Error("RabbitMQ not initialized");
        }
        return RabbitMQ.instance;
    }
    constructor(connectionUri) {
        this.connectionUri = connectionUri;
    }
    connect() {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                this.connection = yield amqplib_1.default.connect(this.connectionUri);
                this.channel = yield this.connection.createChannel();
            }
            catch (err) {
                console.error(err);
            }
        });
    }
    publish(EventType, message) {
        return __awaiter(this, void 0, void 0, function* () {
            const channel = this.getChannel();
            yield (channel === null || channel === void 0 ? void 0 : channel.assertExchange(EventType.exchange, "fanout", {
                durable: true,
            }));
            channel === null || channel === void 0 ? void 0 : channel.publish(EventType.exchange, EventType.routingKey, Buffer.from(JSON.stringify(message)));
        });
    }
    getChannel() {
        return this.channel;
    }
}
exports.RabbitMQ = RabbitMQ;
