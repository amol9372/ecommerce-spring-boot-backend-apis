# Gateway application for Ecommerce Project

## Tech Stack

- Java 17
- Spring boot 3
- Spring Cloud Gateway
- Postgres
- Spring Webflux


## Features

This project includes the follwing features

- JWT Authentication & Authorization with Token generation
- Reactive authentication manager
- Postgres Integration
- API realtime events using websocket (use ws://localhost:8091/ws/events in postman or any language)

## Run application locally

### Set the environment variables

you can set below env variables in a file called `.env`

| Variable Name               | Value                                                                   | Description |
|-----------------------------|-------------------------------------------------------------------------|-------------|
| AUTH0_ISSUER                | https://mydomain.us.auth0.com/                             | URL for the Auth0 issuer used for token validation |
| AUTH0_USERINFO_URL          | https://mydomain.us.auth0.com/userinfo                     | URL to retrieve Auth0 user information |
| POSTGRES_DB                 | sampledb                                                              | The name of your PostgreSQL database |
| POSTGRES_PASSWORD           | password                                                              | Password for your PostgreSQL database |
| POSTGRES_URL                | localhost                     | Hostname & domain of your PostgreSQL database |
| POSTGRES_USER               | postgres                                                                | Username for your PostgreSQL database |


Use the docker file to build and run the application

```bash
docker build -t ecomm-gateway .
```

Run the application

```bash
docker run -it --env-file ./.env -p 8091:8091 -d ecomm-gateway
```

## APIs

Use this postman collection to create & authenticate users
[ecommerce-backend-apis](https://www.postman.com/galactic-eclipse-361945/workspace/public-9372/folder/1877749-451f4b43-f1fc-4f08-98fc-c8e36608ee69?action=share&creator=1877749&ctx=documentation)

