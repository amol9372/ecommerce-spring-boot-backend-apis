
# Spring boot Ecommerce Backend APIs
Ecommerce website based on microservices architecture in spring boot 3

- This project contains backend APIs for ecommerce with different microservices
- It contains all the functionalities like product inventory, variance, cart, checkout etc
- All the APIs are constructed with Spring boot 3

## Postman collection (Public)
```
https://www.postman.com/galactic-eclipse-361945/workspace/public-9372/collection/1877749-6742d038-c937-4aac-838e-7e30ff85865d?action=share&creator=1877749
```

## Features

- Authentication with Jwt
- Add/remove products
- Create categories with hierarchy
- Manage address
- Feature template
- Shopping cart
- Checkout page

## Tech stack

- Java 17
- Spring boot 3
- Elasticsearch 8
- Spring data JPA
- Postgres 14
- Spring cloud gateway
- Docker (docker-compose)

## ER Diagram

<img width="1676" alt="Pasted Graphic" src="https://github.com/amol9372/ecommerce-spring-boot-backend-apis/assets/20081129/94d43c0d-2d2e-40be-a44d-dec762b3ffb2">

## API Root Endpoint

### Spring cloud Gateway

`https://localhost:8091`

### Microservice Endpoints

| Service  | Base URL                           |
|----------|-------------------------------|
| User     | `https://localhost:8080/api`  |
| Product  | `https://localhost:8081/api`  |
| Cart     | `https://localhost:8082/api`  |
| Order    | `https://localhost:8083/api`  |


### Swagger endpoints
| Type  | Endpoint                      |
|----------|-------------------------------|
| Api docs   | `/api/docs`  |
| Swagger UI | `/swagger-ui`  |

## Run Application locally

1. Clone repository

```bash
  git clone git@github.com:amol9372/ecommerce-spring-boot-backend-apis.git
```
2. Set docker & elastic search credentials

```bash
  ./.env and set the Postgres & ElasticSearch credentials
```

3. Go to `ecomm-db` and set the postgres credentials in Dockerfile. Also make sure postgres-data folder is present.         

```bash
./ecomm-db/Dockerfile

-rw-r--r--@  1 amolsingh  staff   139  5 Feb 19:15 Dockerfile
-rw-r--r--@  1 amolsingh  staff  9408  5 Feb 19:15 ecomm.sql
drwxr-xr-x@  2 amolsingh  staff    64  5 Feb 19:16 postgres-data
drwxr-xr-x@  5 amolsingh  staff   160  5 Feb 19:16 .
drwxr-xr-x@ 14 amolsingh  staff   448  5 Feb 19:44 ..
```
4. Run the docker compose command

```bash
./start-services.sh
```
5. Create elastic search index `productv1` using the following command

```bash
./create-index.sh
```
## Onboarding

### APIs setup
Import above postman collection (create a fork also) and lets do the basic setup

#### Register User

- Use the `/auth/register` API to register user in the application
- The above user will have `USER` role. 
- Admin user is already created in the application with below credentials in `users` table

 | email  | password                           |
|----------|-------------------------------|
| admin@example.com     | `password`  |

#### Authenticate user & Get JWT
Use the `/auth/login`  API to authenticate user and get the credentials (jwt token + userinfo)

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImFkbWluQGV4YW1wbGUuY29tIiwicm9sZXMiOlsiQURNSU4iXSwic3ViIjoiYXBwfDk4ZWI2NzRkYTdjNzMzYjIxYWMwZTBkYiIsImlhdCI6MTcwNzMxODY2MywiZXhwIjoxNzA3MzI1ODYzfQ.qEulnxe9IQfOFzO-6F1l81kvy61cNvo4ub3MdurX1Ec",
    "userInfo": {
        "sub": "app|98eb674da7c733b21ac0e0db",
        "name": "admin@example.com",
        "picture": null,
        "email": "admin@example.com",
        "nickname": "admin",
        "email_verified": false
    }
}
```
Use the `token` as in Authorization header for all APIs

#### Headers

Header values are set in `collection variables` 
- Authorization (with bearer token)
- x-token-type (default as `app`) 

#### Reference data 

As a part of starting docker containers, reference data is already created in below entities: 

| Entity            | API                                       | DB Table         |
|-------------------|-------------------------------------------|------------------|
| Category          | `/admin/category`                         | `category`       |
| Feature Template  | `/admin/feature-template/{categoryId}`    | `feature_template`|
| Variant Features  | `/admin/variant/{categoryId}`             | `master_variant` |

## Feature Templates & Product Variants

The application uses the concept of feature templates & product variants

A `feature template` is a list of base line features which a product has
eg - A laptop will have base features as 

| Feature            | Details                             |
|--------------------------|-------------------------------------|
| Brand                    | Apple                               |
| Model Name               | MacBook Pro                         |
| Screen Size              | 14.2 Inches                         |
| CPU Model                | Unknown                             |
| Operating System         | Mac OS                              |
| Special Feature          | Fingerprint Reader                  |
| Graphics Card Description| Integrated                          |

Out of these features, only few can be a part of `variant` features. Variant features are basically what distinguishes the different SKUs of a product like color, storage, memory etc 

An example of variant features 

| Feature            | Details                             |
|--------------------------|-------------------------------------|
| RAM Memory Installed Size| 8 GB                                |
| Colour | Silver | 
| Hard Disk Size | 512 GB |
