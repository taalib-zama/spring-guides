# 🚀 Quick Start Guide

> Get up and running with Spring Guides in 5 minutes!

## For Complete Beginners

### What is This Repository?

Think of this as a **learning playground** for building web applications with Java and Spring Boot. Each folder contains a different project that teaches you specific skills.

**Simple Analogy:** 
- If learning to code is like learning to cook, this repository is your recipe book
- Each project is a different recipe (from simple toast to a full meal)
- You can start with easy recipes and work your way up to complex ones

---

## 🎯 Choose Your Starting Point

### 1️⃣ "I'm Brand New to Spring Boot"

**Start with:** `todo-manager`

**Why?** It's the simplest project - a basic task list application.

```bash
cd springBasicSamples/todo-manager
./mvnw spring-boot:run
```

**What you'll learn:**
- How to create a REST API
- Basic database operations (Create, Read, Update, Delete)
- How Spring Boot works

**Time needed:** 1-2 hours to understand

---

### 2️⃣ "I Know Some Java, New to Spring"

**Start with:** `spring-data-jpa-demo`

**Why?** Teaches you how to work with databases professionally.

```bash
cd springBasicSamples/spring-data-jpa-demo
./mvnw spring-boot:run
```

**What you'll learn:**
- Database connections
- Error handling
- Data validation

**Time needed:** 2-3 hours

---

### 3️⃣ "I Want to Build Something Real"

**Start with:** `electronic-store-backend`

**Why?** A complete e-commerce application with all the bells and whistles.

```bash
cd springBasicSamples/electronicStorePorject/electronic-store-backend
docker-compose up -d  # Start database
./mvnw spring-boot:run
```

**What you'll learn:**
- Building a production-ready application
- User authentication and security
- File uploads
- Shopping cart logic
- API design

**Time needed:** 1-2 days to fully understand

---

## 📦 What You Need Installed

### Required Software

1. **Java 21** - The programming language
   - Download: https://www.oracle.com/java/technologies/downloads/
   - Check if installed: `java -version`

2. **Maven** - Build tool (usually comes with Java)
   - Check if installed: `mvn -version`

3. **Git** - Version control
   - Download: https://git-scm.com/
   - Check if installed: `git --version`

### Optional (But Helpful)

4. **MySQL** - Database (for some projects)
   - Download: https://dev.mysql.com/downloads/

5. **Docker** - For easy database setup
   - Download: https://www.docker.com/

6. **Postman** - For testing APIs
   - Download: https://www.postman.com/

7. **IntelliJ IDEA** or **VS Code** - Code editor
   - IntelliJ: https://www.jetbrains.com/idea/
   - VS Code: https://code.visualstudio.com/

---

## 🏃 Running Your First Project

### Step-by-Step Instructions

1. **Open Terminal/Command Prompt**

2. **Navigate to the project**
   ```bash
   cd spring-guides/springBasicSamples/todo-manager
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   On Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

4. **Wait for this message:**
   ```
   Started Application in X.XXX seconds
   ```

5. **Test it's working**
   - Open browser
   - Go to: `http://localhost:8080`
   - Or use Postman to test the API

---

## 🧪 Testing Your First API

### Using Browser (Simple GET requests)

```
http://localhost:8080/todos
```

### Using cURL (Command Line)

```bash
# Get all todos
curl http://localhost:8080/todos

# Create a new todo
curl -X POST http://localhost:8080/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"My First Todo","content":"Learn Spring Boot"}'
```

### Using Postman (Recommended for Beginners)

1. Open Postman
2. Create new request
3. Set method to `GET`
4. Enter URL: `http://localhost:8080/todos`
5. Click "Send"

---

## 📚 Understanding the Code Structure

Every project follows this pattern:

```
project-name/
├── src/
│   ├── main/
│   │   ├── java/              ← Your Java code
│   │   │   └── com/sample/
│   │   │       ├── controller/  ← API endpoints
│   │   │       ├── service/     ← Business logic
│   │   │       ├── repository/  ← Database access
│   │   │       └── entity/      ← Database tables
│   │   └── resources/
│   │       └── application.properties  ← Configuration
│   └── test/                  ← Test code
└── pom.xml                    ← Dependencies
```

**What each folder does:**

- **Controller**: Handles web requests (like "get all todos")
- **Service**: Contains business logic (like "validate todo before saving")
- **Repository**: Talks to the database
- **Entity**: Represents database tables as Java classes

---

## 🎓 Learning Path

### Week 1: Basics
- [ ] Run `todo-manager` project
- [ ] Understand REST APIs
- [ ] Learn CRUD operations
- [ ] Read the code and comments

### Week 2: Database
- [ ] Run `spring-data-jpa-demo`
- [ ] Learn about JPA and Hibernate
- [ ] Understand database relationships
- [ ] Practice writing queries

### Week 3: Security
- [ ] Run `authSamples/authSample`
- [ ] Learn about authentication
- [ ] Understand password encryption
- [ ] Try JWT tokens

### Week 4: Full Application
- [ ] Run `electronic-store-backend`
- [ ] Study the architecture
- [ ] Test all APIs
- [ ] Try to add a new feature

---

## 🆘 Common Problems & Solutions

### Problem: "Port 8080 already in use"

**Solution:** Another application is using that port.

```bash
# Option 1: Stop the other application
# Option 2: Change the port
# Add this to application.properties:
server.port=8081
```

### Problem: "mvnw: command not found"

**Solution:** Use the full path or use Maven directly.

```bash
# Instead of ./mvnw
# Use:
mvn spring-boot:run
```

### Problem: "Database connection failed"

**Solution:** Make sure MySQL is running or use H2 (in-memory database).

```properties
# Add to application.properties for H2:
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
```

### Problem: "Java version mismatch"

**Solution:** Check your Java version.

```bash
java -version
# Should show Java 21 or higher
```

---

## 🎯 Next Steps

After running your first project:

1. **Read the code** - Start with the Controller classes
2. **Make small changes** - Try adding a new field
3. **Break things** - Best way to learn! (You can always reset)
4. **Read documentation** - Each project has its own README
5. **Ask questions** - Open an issue if stuck

---

## 📖 Helpful Resources

### Official Documentation
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)

### Tutorials
- [Spring Boot Tutorial](https://spring.io/guides/gs/spring-boot/)
- [REST API Tutorial](https://spring.io/guides/gs/rest-service/)
- [JPA Tutorial](https://spring.io/guides/gs/accessing-data-jpa/)

### Tools
- [Postman Learning](https://learning.postman.com/)
- [Git Tutorial](https://git-scm.com/docs/gittutorial)
- [Maven Guide](https://maven.apache.org/guides/getting-started/)

---

## 💡 Pro Tips

1. **Start Small** - Don't try to understand everything at once
2. **Run First, Understand Later** - Get it working, then figure out how
3. **Use Debugger** - Set breakpoints and step through code
4. **Read Error Messages** - They usually tell you what's wrong
5. **Google is Your Friend** - Most errors have been solved before
6. **Take Breaks** - Coding is mentally intensive

---

## 🎉 You're Ready!

Pick a project, run it, and start learning. Remember:
- Everyone starts as a beginner
- Making mistakes is part of learning
- The best way to learn is by doing

**Happy Coding! 🚀**

---

*Need help? Open an issue or check the main README.md*
