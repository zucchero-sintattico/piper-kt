"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Validate = void 0;
const errors_1 = require("./errors");
function checkFields(object, schema) {
    const missing = [];
    for (const [key, value] of Object.entries(schema)) {
        if (!(key in object) && !value.endsWith("?")) {
            missing.push(key);
        }
    }
    return missing;
}
function Validate(schema) {
    return (req, res, next) => {
        var _a;
        const params = req.params;
        const body = req.body;
        // Build a response with all the errors
        const paramsError = checkFields(params, schema.Params);
        const bodyError = checkFields(body, schema.Body);
        const queryError = checkFields(req.query, (_a = schema.Query) !== null && _a !== void 0 ? _a : {});
        if (paramsError.length > 0 ||
            bodyError.length > 0 ||
            queryError.length > 0) {
            const response = new errors_1.BadRequest(paramsError, bodyError, queryError);
            response.send(res);
        }
        else {
            next();
        }
    };
}
exports.Validate = Validate;
