# Create product (POST /products)
curl -i -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "title": "Sample Product",
    "description": "Example description",
    "price": 199.99,
    "productImage": "image.jpg",
    "addedDate": "2025-01-01",
    "discountedPrice": 149.99,
    "isLive": true,
    "isInstock": true
  }'

# Update product (PUT /products/{productId})
curl -i -X PUT http://localhost:8080/products/{productId} \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "title": "Updated Title",
    "price": 179.99,
    "isLive": false
  }'

# Delete product (DELETE /products/{productId})
curl -i -X DELETE http://localhost:8080/products/{productId} \
  -H "Accept: application/json"

# Get single product (GET /products/{productId})
curl -i -X GET http://localhost:8080/products/{productId} \
  -H "Accept: application/json"

# Get all products with pagination & sorting (GET /products)
# Example: page 0, size 10, sort by addedDate descending
curl -i -X GET "http://localhost:8080/products?page=0&size=10&sort=addedDate,desc" \
  -H "Accept: application/json"

# Get all live products with pagination & sorting (GET /products/live)
curl -i -X GET "http://localhost:8080/products/live?page=0&size=10&sort=addedDate,desc" \
  -H "Accept: application/json"

# Search products by subTitle with pagination & sorting (GET /products/search)
# Example searching for "phone"
curl -i -X GET "http://localhost:8080/products/search?subTitle=phone&page=0&size=10&sort=addedDate,desc" \
  -H "Accept: application/json"
