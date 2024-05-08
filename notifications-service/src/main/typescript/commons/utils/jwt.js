"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.JWTRefreshTokenMiddleware = exports.JWTAuthenticationMiddleware = exports.verifyRefreshToken = exports.isAccessTokenValid = exports.decodeAccessToken = exports.verifyAccessToken = exports.generateRefreshToken = exports.generateAccessToken = void 0;
const jsonwebtoken_1 = __importDefault(require("jsonwebtoken"));
const errors_1 = require("../../api/errors");
const ACCESS_TOKEN_SECRET = process.env["ACCESS_TOKEN_SECRET"] || "access";
const REFRESH_TOKEN_SECRET = process.env["REFRESH_TOKEN_SECRET"] || "refresh";
/**
 * Generate a JWT Access Token for the user
 * @param user User to generate the token
 * @param expiresIn Expiration time, default 1 day
 * @returns JWT Access Token
 */
const generateAccessToken = (user, expiresIn = "1d") => {
    return jsonwebtoken_1.default.sign({ username: user.username, email: user.email }, ACCESS_TOKEN_SECRET, { expiresIn: expiresIn });
};
exports.generateAccessToken = generateAccessToken;
/**
 * Generate a JWT Refresh Token for the user
 * @param user User to generate the token
 * @param expiresIn Expiration time, default 1 week
 * @returns JWT Refresh Token
 */
const generateRefreshToken = (user, expiresIn = "1w") => {
    return jsonwebtoken_1.default.sign({ username: user.username, email: user.email }, REFRESH_TOKEN_SECRET, { expiresIn: expiresIn });
};
exports.generateRefreshToken = generateRefreshToken;
/**
 * Verify a JWT Access Token
 * @param token JWT Access Token
 * @returns Decoded JWT Access Token
 * @throws Error if the token is invalid
 * @throws Error if the token is expired
 */
const verifyAccessToken = (token) => {
    return jsonwebtoken_1.default.verify(token, ACCESS_TOKEN_SECRET);
};
exports.verifyAccessToken = verifyAccessToken;
const decodeAccessToken = (token) => {
    return jsonwebtoken_1.default.decode(token);
};
exports.decodeAccessToken = decodeAccessToken;
const isAccessTokenValid = (token) => {
    try {
        jsonwebtoken_1.default.verify(token, ACCESS_TOKEN_SECRET);
        return true;
    }
    catch (e) {
        return false;
    }
};
exports.isAccessTokenValid = isAccessTokenValid;
/**
 * Verify a JWT Refresh Token
 * @param token JWT Refresh Token
 * @returns Decoded JWT Refresh Token
 * @throws Error if the token is invalid
 * @throws Error if the token is expired
 */
const verifyRefreshToken = (token) => {
    return jsonwebtoken_1.default.verify(token, REFRESH_TOKEN_SECRET);
};
exports.verifyRefreshToken = verifyRefreshToken;
/**
 * Middleware to check if the JWT Access Token is present and valid
 * @param req Express Request
 * @param res Express Response
 * @param next Express Next Function
 * @returns 401 if the JWT Access Token is missing
 * @returns 401 if the JWT Access Token is invalid
 * @returns 401 if the JWT Access Token is expired
 */
const JWTAuthenticationMiddleware = (req, res, next) => {
    const accessToken = req.cookies.jwt;
    if (!accessToken) {
        const response = new errors_1.JwtTokenMissingOrInvalid();
        response.send(res);
        return;
    }
    try {
        req.user = jsonwebtoken_1.default.verify(accessToken, ACCESS_TOKEN_SECRET);
        next();
    }
    catch (e) {
        const response = new errors_1.JwtTokenMissingOrInvalid();
        response.send(res);
    }
};
exports.JWTAuthenticationMiddleware = JWTAuthenticationMiddleware;
/**
 * Middleware to check if the JWT Access Token is present and expired
 * This middleware is used to check if the user can refresh the token
 * @param req Express Request
 * @param res Express Response
 * @param next Express Next Function
 * @returns 401 if the JWT Access Token is missing
 * @returns 400 if the JWT Access Token is valid
 */
const JWTRefreshTokenMiddleware = (req, res, next) => {
    const accessToken = req.cookies.jwt;
    if (!accessToken) {
        const response = new errors_1.JwtTokenMissingOrInvalid();
        response.send(res);
        return;
    }
    req.user = jsonwebtoken_1.default.decode(accessToken);
    next();
    return;
};
exports.JWTRefreshTokenMiddleware = JWTRefreshTokenMiddleware;
