FROM node:22.15.1-alpine
WORKDIR /home/app
COPY --link dist /home/app/dist
COPY --link node_modules /home/app/node_modules
ENTRYPOINT ["node", "/home/app/dist/app.js"]
