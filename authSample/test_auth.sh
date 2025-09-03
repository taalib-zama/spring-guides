#!/bin/bash

echo "Testing Authentication Flow..."

# 1. Register a user
echo "1. Registering user..."
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "testpass",
    "role": "USER"
  }'

echo -e "\n\n2. Testing login with correct credentials..."
curl -X POST http://localhost:8080/auth/login \
  -u testuser:testpass

echo -e "\n\n3. Testing protected endpoint..."
curl -X GET http://localhost:8080/auth/profile \
  -u testuser:testpass

echo -e "\n\n4. Testing with wrong credentials (should fail)..."
curl -X GET http://localhost:8080/auth/profile \
  -u testuser:wrongpass