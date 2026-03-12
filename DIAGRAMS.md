# 🎨 Visual Diagrams Reference

> A collection of diagrams to help visualize Spring Boot concepts

## 📋 Table of Contents

- [Repository Structure](#repository-structure)
- [Application Flow](#application-flow)
- [Database Relationships](#database-relationships)
- [Security Flow](#security-flow)
- [Request Lifecycle](#request-lifecycle)

---

## 🗂️ Repository Structure

### Overall Organization

```
spring-guides/
│
├── 📚 Documentation Files
│   ├── README.md ...................... Main entry point
│   ├── QUICKSTART.md .................. Beginner guide
│   ├── PROJECT_COMPARISON.md .......... Project selector
│   ├── ARCHITECTURE.md ................ Design guide
│   ├── DOCUMENTATION_INDEX.md ......... Navigation
│   └── DIAGRAMS.md .................... This file
│
├── 🔐 authSamples/
│   ├── authSample/ .................... Basic auth + JWT
│   └── demo-for-basic-auth/ ........... Auth with database
│
├── 🌱 springBasicSamples/
│   ├── todo-manager/ .................. Simple CRUD app
│   ├── spring-data-jpa-demo/ .......... Database operations
│   ├── async-demo/ .................... Async processing
│   ├── demo-core-concepts/ ............ Core Spring concepts
│   ├── learn-spring-orm/ .............. ORM examples
│   ├── SpringMvcProject/ .............. MVC pattern
│   └── electronicStorePorject/
│       ├── electronic-store-backend/ .. Full e-commerce API
│       ├── frontend-app/ .............. Frontend (if any)
│       └── Documentation.md ........... Complete guide
│
├── 🔄 spring-transaction-sample/ ...... Transaction management
├── 🏗️ demo-from-app-architecture/ ..... Architecture patterns
└── 📁 uploads/ ........................ File storage
```

---

## 🔄 Application Flow

### Standard Spring Boot Request Flow

```
┌─────────────────────────────────────────────────────────────┐
│                         CLIENT                              │
│                    (Browser/Mobile)                         │
└────────────────────────┬────────────────────────────────────┘
                         │
                         │ HTTP Request
                         │ (JSON/Form Data)
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                    SPRING BOOT APP                          │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              1. SECURITY FILTER                       │  │
│  │  • Validate JWT Token                                 │  │
│  │  • Check Authentication                               │  │
│  │  • Verify Permissions                                 │  │
│  └─────────────────────┬─────────────────────────────────┘  │
│                        │                                     │
│                        ▼                                     │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              2. CONTROLLER LAYER                      │  │
│  │  • Receive HTTP Request                               │  │
│  │  • Validate Input                                     │  │
│  │  • Map to Java Objects                                │  │
│  │  • Call Service Layer                                 │  │
│  └─────────────────────┬─────────────────────────────────┘  │
│                        │                                     │
│                        ▼                                     │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              3. SERVICE LAYER                         │  │
│  │  • Business Logic                                     │  │
│  │  • Validation Rules                                   │  │
│  │  • Data Transformation                                │  │
│  │  • Call Repository                                    │  │
│  └─────────────────────┬─────────────────────────────────┘  │
│                        │                                     │
│                        ▼                                     │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              4. REPOSITORY LAYER                      │  │
│  │  • JPA/Hibernate                                      │  │
│  │  • Generate SQL                                       │  │
│  │  • Execute Queries                                    │  │
│  └─────────────────────┬─────────────────────────────────┘  │
└────────────────────────┼─────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                      DATABASE                               │
│                    (MySQL/H2/PostgreSQL)                    │
└────────────────────────┬────────────────────────────────────┘
                         │
                         │ Results
                         ▼
                    (Flow back up)
```

---

## 🗄️ Database Relationships

### Electronic Store Database Schema

```
┌──────────────────────┐
│       USERS          │
├──────────────────────┤
│ PK: id               │
│     name             │
│     email (unique)   │
│     password         │
│     gender           │
│     about            │
│     imageName        │
│     roles            │
└──────┬───────────────┘
       │
       │ 1:N (One user has many products)
       │
       ▼
┌──────────────────────┐         ┌──────────────────────┐
│     PRODUCTS         │    N:1  │     CATEGORIES       │
├──────────────────────┤◄────────┤──────────────────────┤
│ PK: id               │         │ PK: id               │
│ FK: userId           │         │     title            │
│ FK: categoryId       │─────────│     description      │
│     title            │         │     coverImage       │
│     description      │         └──────────────────────┘
│     price            │
│     quantity         │
│     productImage     │
│     live             │
│     stock            │
│     addedDate        │
└──────┬───────────────┘
       │
       │ N:M (Many products in many carts)
       │
       ▼
┌──────────────────────┐         ┌──────────────────────┐
│     CART_ITEMS       │    N:1  │       CARTS          │
├──────────────────────┤◄────────┤──────────────────────┤
│ PK: id               │         │ PK: id               │
│ FK: cartId           │─────────│ FK: userId           │
│ FK: productId        │         │     createdAt        │
│     quantity         │         └──────────────────────┘
│     totalPrice       │                  ▲
└──────────────────────┘                  │
                                          │ 1:1
                                          │
                                    ┌─────┴────────┐
                                    │    USERS     │
                                    └──────────────┘
```

### Relationship Types Explained

```
1:1 (One-to-One)
User ←→ Cart
Each user has exactly one cart

1:N (One-to-Many)
User ─→ Products
One user can create many products

N:1 (Many-to-One)
Products ─→ Category
Many products belong to one category

N:M (Many-to-Many)
Products ←→ Carts (through CartItems)
Many products can be in many carts
```

---

## 🔐 Security Flow

### JWT Authentication Process

```
┌─────────────────────────────────────────────────────────────┐
│                    REGISTRATION FLOW                        │
└─────────────────────────────────────────────────────────────┘

User Input                    Server Processing
┌──────────┐                 ┌──────────────────┐
│ Username │                 │ Validate Input   │
│ Email    │────────────────▶│ Check Duplicates │
│ Password │                 │ Encrypt Password │
└──────────┘                 │ (BCrypt)         │
                             │ Save to DB       │
                             └────────┬─────────┘
                                      │
                                      ▼
                             ┌──────────────────┐
                             │ Return Success   │
                             └──────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                      LOGIN FLOW                             │
└─────────────────────────────────────────────────────────────┘

Step 1: User Submits Credentials
┌──────────────┐
│ POST /login  │
│ {            │
│   email,     │
│   password   │
│ }            │
└──────┬───────┘
       │
       ▼
Step 2: Server Validates
┌────────────────────────┐
│ Find user by email     │
│ Compare passwords      │
│ (BCrypt.matches)       │
└──────┬─────────────────┘
       │
       ▼
Step 3: Generate JWT Token
┌────────────────────────┐
│ Create JWT with:       │
│ • User ID              │
│ • Email                │
│ • Roles                │
│ • Expiration (24h)     │
│ • Sign with secret     │
└──────┬─────────────────┘
       │
       ▼
Step 4: Return Token
┌────────────────────────┐
│ {                      │
│   "token": "eyJ...",   │
│   "user": {...}        │
│ }                      │
└────────────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                  AUTHENTICATED REQUEST FLOW                 │
└─────────────────────────────────────────────────────────────┘

Step 1: Client Sends Request with Token
┌─────────────────────────────────┐
│ GET /api/products               │
│ Headers:                        │
│   Authorization: Bearer eyJ...  │
└────────────┬────────────────────┘
             │
             ▼
Step 2: Security Filter Intercepts
┌─────────────────────────────────┐
│ Extract token from header       │
│ Validate token signature        │
│ Check expiration                │
│ Extract user info               │
└────────────┬────────────────────┘
             │
             ▼
Step 3: Authorization Check
┌─────────────────────────────────┐
│ Check user roles                │
│ Verify permissions              │
│ Allow/Deny access               │
└────────────┬────────────────────┘
             │
             ▼
Step 4: Process Request
┌─────────────────────────────────┐
│ Controller → Service → Repo     │
│ Return response                 │
└─────────────────────────────────┘
```

---

## 🔄 Request Lifecycle

### Complete Request-Response Cycle

```
TIME →

T0: Client Sends Request
    │
    │  POST /api/products
    │  Authorization: Bearer token123
    │  { "title": "iPhone", "price": 999 }
    │
    ▼
T1: Request Hits Server
    │
    ├─→ Security Filter
    │   ├─ Validate JWT token
    │   ├─ Extract user info
    │   └─ Check permissions ✓
    │
    ▼
T2: Controller Receives Request
    │
    │  @PostMapping("/products")
    │  public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto)
    │
    ├─→ Validate input
    │   ├─ Check required fields
    │   ├─ Validate data types
    │   └─ Check constraints ✓
    │
    ▼
T3: Service Layer Processing
    │
    │  productService.createProduct(dto)
    │
    ├─→ Business Logic
    │   ├─ Check if product exists
    │   ├─ Validate price > 0
    │   ├─ Set default values
    │   └─ Convert DTO to Entity
    │
    ▼
T4: Repository Layer
    │
    │  productRepository.save(product)
    │
    ├─→ JPA/Hibernate
    │   ├─ Generate SQL
    │   │  INSERT INTO products (title, price, ...)
    │   │  VALUES ('iPhone', 999, ...)
    │   └─ Execute query
    │
    ▼
T5: Database Operation
    │
    ├─→ MySQL executes INSERT
    ├─→ Returns generated ID
    └─→ Commits transaction ✓
    │
    ▼
T6: Response Flows Back
    │
    │  Database → Repository → Service → Controller
    │
    ├─→ Each layer transforms data
    │   ├─ Entity → DTO
    │   ├─ Add metadata
    │   └─ Format response
    │
    ▼
T7: HTTP Response Sent
    │
    │  201 Created
    │  {
    │    "id": 123,
    │    "title": "iPhone",
    │    "price": 999,
    │    "createdAt": "2025-01-15T10:30:00"
    │  }
    │
    ▼
T8: Client Receives Response
```

---

## 🛒 Shopping Cart Flow

### Adding Item to Cart

```
┌─────────────────────────────────────────────────────────────┐
│                  ADD TO CART SEQUENCE                       │
└─────────────────────────────────────────────────────────────┘

User Action: Click "Add to Cart"
│
▼
┌────────────────────────────────────────┐
│ Frontend sends request:                │
│ POST /api/carts/{userId}/items         │
│ {                                      │
│   "productId": "prod123",              │
│   "quantity": 2                        │
│ }                                      │
└────────────┬───────────────────────────┘
             │
             ▼
┌────────────────────────────────────────┐
│ CartController receives request        │
└────────────┬───────────────────────────┘
             │
             ▼
┌────────────────────────────────────────┐
│ CartService.addItemToCart()            │
│                                        │
│ Step 1: Get or create cart            │
│   ├─ Find cart by userId               │
│   └─ If not exists, create new cart    │
│                                        │
│ Step 2: Validate product              │
│   ├─ Check product exists              │
│   ├─ Check product is in stock         │
│   └─ Check quantity available          │
│                                        │
│ Step 3: Check if item already in cart │
│   ├─ If yes: Update quantity           │
│   └─ If no: Create new cart item       │
│                                        │
│ Step 4: Calculate prices              │
│   ├─ itemPrice = product.price         │
│   ├─ totalPrice = itemPrice * quantity │
│   └─ Update cart total                 │
│                                        │
│ Step 5: Save to database              │
│   ├─ Save cart item                    │
│   └─ Update cart                       │
└────────────┬───────────────────────────┘
             │
             ▼
┌────────────────────────────────────────┐
│ Return updated cart with all items     │
│ {                                      │
│   "cartId": "cart123",                 │
│   "items": [                           │
│     {                                  │
│       "product": {...},                │
│       "quantity": 2,                   │
│       "totalPrice": 1998               │
│     }                                  │
│   ],                                   │
│   "cartTotal": 1998                    │
│ }                                      │
└────────────────────────────────────────┘
```

---

## 🔄 Async Processing

### Synchronous vs Asynchronous

```
SYNCHRONOUS (Traditional)
─────────────────────────

User Request → Process → Wait → Wait → Wait → Response
               (5 seconds)
               
Total Time: 5 seconds
User must wait for everything to complete


ASYNCHRONOUS (Better)
─────────────────────

User Request → Start Process → Immediate Response
                     ↓
               (Background)
                     ↓
               Complete Later
               
Total Time: 0.1 seconds (for response)
User doesn't wait, gets notification when done
```

### Async Flow Diagram

```
┌──────────────────────────────────────────────────────────┐
│                    MAIN THREAD                           │
├──────────────────────────────────────────────────────────┤
│                                                          │
│  User Request                                            │
│       │                                                  │
│       ▼                                                  │
│  Controller receives                                     │
│       │                                                  │
│       ▼                                                  │
│  Call @Async method ────────────┐                       │
│       │                          │                       │
│       ▼                          │                       │
│  Return immediately              │                       │
│       │                          │                       │
│       ▼                          │                       │
│  Send response to user           │                       │
│                                  │                       │
└──────────────────────────────────┼───────────────────────┘
                                   │
                                   │ Spawns
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────┐
│                  BACKGROUND THREAD                       │
├──────────────────────────────────────────────────────────┤
│                                                          │
│  Execute long-running task                               │
│       │                                                  │
│       ├─→ Send email                                     │
│       ├─→ Process file                                   │
│       ├─→ Call external API                              │
│       └─→ Generate report                                │
│                                                          │
│  Task completes                                          │
│       │                                                  │
│       ▼                                                  │
│  Notify user (optional)                                  │
│                                                          │
└──────────────────────────────────────────────────────────┘
```

---

## 📊 Data Transformation

### Entity ↔ DTO Conversion

```
┌─────────────────────────────────────────────────────────────┐
│                    DATABASE LAYER                           │
│                                                             │
│  Product Entity (Full data)                                 │
│  ┌──────────────────────────────────────┐                  │
│  │ id: 123                              │                  │
│  │ title: "iPhone 15"                   │                  │
│  │ description: "Latest iPhone..."      │                  │
│  │ price: 999.99                        │                  │
│  │ quantity: 50                         │                  │
│  │ categoryId: "cat456"                 │                  │
│  │ userId: "user789"                    │                  │
│  │ productImage: "iphone.jpg"           │                  │
│  │ live: true                           │                  │
│  │ stock: true                          │                  │
│  │ addedDate: "2025-01-15"              │                  │
│  │ internalNotes: "Supplier: Apple"     │ ← Internal only  │
│  │ costPrice: 700.00                    │ ← Internal only  │
│  └──────────────────────────────────────┘                  │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      │ MapStruct / ModelMapper
                      │ Converts & Filters
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                            │
│                                                             │
│  Product DTO (Public data)                                  │
│  ┌──────────────────────────────────────┐                  │
│  │ id: 123                              │                  │
│  │ title: "iPhone 15"                   │                  │
│  │ description: "Latest iPhone..."      │                  │
│  │ price: 999.99                        │                  │
│  │ quantity: 50                         │                  │
│  │ category: {                          │ ← Nested object  │
│  │   id: "cat456",                      │                  │
│  │   title: "Smartphones"               │                  │
│  │ }                                    │                  │
│  │ productImage: "iphone.jpg"           │                  │
│  │ inStock: true                        │                  │
│  │                                      │                  │
│  │ ❌ No internal fields                │                  │
│  │ ❌ No cost price                     │                  │
│  └──────────────────────────────────────┘                  │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      │ JSON Serialization
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    API RESPONSE                             │
│                                                             │
│  {                                                          │
│    "id": 123,                                               │
│    "title": "iPhone 15",                                    │
│    "description": "Latest iPhone...",                       │
│    "price": 999.99,                                         │
│    "quantity": 50,                                          │
│    "category": {                                            │
│      "id": "cat456",                                        │
│      "title": "Smartphones"                                 │
│    },                                                       │
│    "productImage": "iphone.jpg",                            │
│    "inStock": true                                          │
│  }                                                          │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎯 Summary

These diagrams illustrate:

✅ **Repository Structure** - How files are organized  
✅ **Application Flow** - How requests are processed  
✅ **Database Design** - How data is related  
✅ **Security** - How authentication works  
✅ **Request Lifecycle** - Complete journey of a request  
✅ **Cart Operations** - E-commerce functionality  
✅ **Async Processing** - Background task handling  
✅ **Data Transformation** - Entity to DTO conversion  

---

**Use these diagrams as reference while studying the code!**

*Last Updated: 2025*
