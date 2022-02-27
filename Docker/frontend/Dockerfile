FROM node:16.9.1-alpine

RUN mkdir /frontend
WORKDIR /frontend

COPY frontend /frontend/

RUN yarn install

EXPOSE 3000