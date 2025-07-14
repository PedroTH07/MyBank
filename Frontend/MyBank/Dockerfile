FROM node:22-alpine AS build

WORKDIR /app

COPY package*.json .

RUN npm install

COPY . .

RUN npm run build

FROM node:22-alpine

WORKDIR /app

COPY --from=build /app/dist /app/dist
COPY --from=build /app/package*.json .

RUN npm install --only=production

EXPOSE 4000

CMD ["npm", "run", "serve:ssr:MyBank"]