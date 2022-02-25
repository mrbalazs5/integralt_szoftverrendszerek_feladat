FROM node:16.9.1-alpine

RUN mkdir /frontend
WORKDIR /frontend

COPY frontend /frontend/

RUN npm install

RUN chown -R node.node /frontend

USER node

RUN chmod -R 777 /frontend

EXPOSE 3000