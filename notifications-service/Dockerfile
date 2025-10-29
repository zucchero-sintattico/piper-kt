FROM node:22.19.0-alpine
WORKDIR /home/app
COPY --link dist /home/app/dist
COPY --link node_modules /home/app/node_modules
ENTRYPOINT ["node", "/home/app/dist/app.js"]
