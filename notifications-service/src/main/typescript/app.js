"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const service_1 = require("./commons/service");
const configuration_1 = require("./configuration");
const service = new service_1.Microservice(configuration_1.NotificationsServiceConfiguration);
service.start();
