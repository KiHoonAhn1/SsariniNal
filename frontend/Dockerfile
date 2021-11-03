FROM node:14.17.4-alpine AS build-stage
MAINTAINER Taehyun Kim <sskl600@naver.com>

WORKDIR /app
COPY . .
RUN yarn install
RUN yarn run build

FROM nginx:stable-alpine AS production-stage
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80 443
CMD ["nginx", "-g", "daemon off;"]
