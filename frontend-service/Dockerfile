FROM node:22.11.0-alpine
WORKDIR /home/app
COPY --link dist /home/app/dist
COPY --link node_modules /home/app/node_modules
ENV PORT=8080
EXPOSE $PORT
RUN npm install -g serve@14.2.3
ENTRYPOINT serve -s dist -l $PORT --no-clipboard
