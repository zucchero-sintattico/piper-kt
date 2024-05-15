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
exports.ServiceEvents = void 0;
class ServiceEvents {
    static initialize(Rabbit, configuration) {
        return __awaiter(this, void 0, void 0, function* () {
            const broker = Rabbit;
            for (const exchange of Object.keys(configuration.exchanges)) {
                const exchangeConfig = configuration.exchanges[exchange];
                const channel = broker.getChannel();
                yield (channel === null || channel === void 0 ? void 0 : channel.assertExchange(exchange, "fanout", { durable: true }));
                const queue = yield (channel === null || channel === void 0 ? void 0 : channel.assertQueue("", { exclusive: true }));
                if (!queue) {
                    throw new Error("Could not assert queue");
                }
                yield (channel === null || channel === void 0 ? void 0 : channel.bindQueue(queue.queue, exchange, ""));
                yield (channel === null || channel === void 0 ? void 0 : channel.consume(queue.queue, (message) => {
                    if (message) {
                        try {
                            const data = JSON.parse(message.content.toString());
                            const routingKey = message.fields.routingKey;
                            const event = exchangeConfig.events[routingKey];
                            if (event) {
                                event(data);
                            }
                        }
                        catch (error) {
                            console.error(error);
                        }
                    }
                }, { noAck: true }));
            }
        });
    }
}
exports.ServiceEvents = ServiceEvents;
