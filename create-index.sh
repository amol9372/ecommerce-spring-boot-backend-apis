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

