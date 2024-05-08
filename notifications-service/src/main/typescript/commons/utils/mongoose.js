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
exports.MongooseUtils = void 0;
class MongooseUtils {
    static initialize(mongoose, connectionUri) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                yield mongoose.connect(connectionUri);
                console.log("Connected to MongoDB");
            }
            catch (err) {
                console.error(err);
            }
        });
    }
    static close(mongoose) {
        return __awaiter(this, void 0, void 0, function* () {
            yield mongoose.disconnect();
        });
    }
    static clear(mongoose) {
        return __awaiter(this, void 0, void 0, function* () {
            const collections = mongoose.connection.collections;
            for (const key in collections) {
                const collection = collections[key];
                yield (collection === null || collection === void 0 ? void 0 : collection.deleteMany({}));
            }
        });
    }
}
exports.MongooseUtils = MongooseUtils;
