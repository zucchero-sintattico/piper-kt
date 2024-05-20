FROM node:20.13.1-alpine
WORKDIR /home/app
COPY --link dist /home/app/dist
COPY --link node_modules /home/app/node_modules
COPY --link build /home/app/build
ENTRYPOINT ["node", "/home/app/dist/app.js"]
