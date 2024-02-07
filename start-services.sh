#!/bin/bash

# Remove the postgres-data directory
echo "Removing existing postgres-data directory..."
#rm -rf ./ecomm-db/postgres-data

# Stopping all containers
echo "Removing Docker Compose services..."
docker-compose down

# Build containers
echo "Building Docker Compose services..."
docker-compose build


# Start Docker Compose services
echo "Starting Docker Compose services..."
docker-compose up -d
