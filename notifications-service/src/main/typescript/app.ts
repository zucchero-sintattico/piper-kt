import { Microservice } from "./commons/service";
import { NotificationsServiceConfiguration } from "./configuration";

process.on("SIGTERM", async () => {
  console.log("Shutting down notifications service");
  process.exit(0);
});

const service = new Microservice(NotificationsServiceConfiguration);
service.start();
