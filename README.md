
# Spring boot Ecommerce Backend APIs
Ecommerce website based on microservices architecture in spring boot 3

- This project contains backend APIs for ecommerce with different microservices
- It contains all the functionalities like product inventory, variance, cart, checkout etc
- All the APIs are constructed with Spring boot 3

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

## ER Diagram

<img width="1676" alt="Pasted Graphic" src="https://github.com/amol9372/ecommerce-spring-boot-backend-apis/assets/20081129/94d43c0d-2d2e-40be-a44d-dec762b3ffb2">

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

## Onboarding

1. Create users

   - Create a user using the `/auth/register` endpoint
   - In the DB table `user_roles` set the role as `ADMIN`
  
2. Create elastic search index using the following command

```bash
#!/bin/bash

curl --location --request PUT 'localhost:9200/productv1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic ZWxhc3RpYzpEa0llZFBQU0Ni' \
--data '{
    "settings": {
        "analysis": {
            "filter": {
                "autocomplete_filter": {
                    "type": "edge_ngram",
                    "min_gram": 1,
                    "max_gram": 20
                }
            },
            "analyzer": {
                "autocomplete": {
                    "type": "custom",
                    "tokenizer": "standard",
                    "filter": [
                        "lowercase"
                    ]
                }
            }
        }
    },
    "mappings": {
        "properties": {
            "category_tree": {
                "type": "keyword"
            },
            "id": {
                "type": "integer"
            },
            "description": {
                "type": "text"
            },
            "name": {
                "type": "text",
                "analyzer": "autocomplete",
                "search_analyzer": "standard"
            },
            "price": {
                "type": "double"
            },
            "updated_on": {
                "type": "date"
            },
            "created_on": {
                "type": "date"
            }
        }
    }
}'
```     

      

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
