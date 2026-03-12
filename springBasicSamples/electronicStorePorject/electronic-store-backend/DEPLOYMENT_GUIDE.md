# 🚀 Electronic Store - Azure VM Deployment Guide

> Complete guide documenting the deployment of Electronic Store Backend on Azure Virtual Machine using Docker

## 📋 Table of Contents

- [Overview](#overview)
- [Deployment Architecture](#deployment-architecture)
- [Prerequisites](#prerequisites)
- [Phase 1: Dockerizing the Application](#phase-1-dockerizing-the-application)
- [Phase 2: Publishing to Docker Hub](#phase-2-publishing-to-docker-hub)
- [Phase 3: Azure VM Setup](#phase-3-azure-vm-setup)
- [Phase 4: Running on Azure VM](#phase-4-running-on-azure-vm)
- [Phase 5: Network Configuration](#phase-5-network-configuration)
- [Phase 6: Testing & Verification](#phase-6-testing--verification)
- [Troubleshooting](#troubleshooting)
- [Useful Commands](#useful-commands)

---

## 🎯 Overview

This guide documents the actual deployment process of the Electronic Store Backend application on Microsoft Azure using:
- **Docker** for containerization
- **Docker Hub** for image registry
- **Azure Virtual Machine** for hosting
- **Docker Network** for container communication
- **MySQL 8.0** container for database

**Deployment Method:** Azure VM with Docker containers

**Live Application URL:** `http://130.107.145.195:8080`

---

## 🏗️ Deployment Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    LOCAL DEVELOPMENT                        │
│                                                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Spring Boot Application                             │  │
│  │  + Dockerfile                                        │  │
│  │  + docker-compose.yml                                │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │                                     │
│                       │ Build & Push                        │
│                       ▼                                     │
└─────────────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────────┐
│                    DOCKER HUB                               │
│                                                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  taalib1705/electronicstore1.0                       │  │
│  │  taalib1705/electronicstorefull1.0                   │  │
│  └────────────────────┬─────────────────────────────────┘  │
└────────────────────────┼─────────────────────────────────────┘
                        │
                        │ Pull Image
                        ▼
┌─────────────────────────────────────────────────────────────┐
│                    AZURE CLOUD                              │
│                                                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │           Azure Virtual Machine (Linux)              │  │
│  │                                                      │  │
│  │  ┌────────────────────────────────────────────────┐ │  │
│  │  │  Docker Network: electronic-store-network      │ │  │
│  │  │                                                │ │  │
│  │  │  ┌──────────────────┐  ┌──────────────────┐  │ │  │
│  │  │  │  MySQL Container │  │  App Container   │  │ │  │
│  │  │  │  Port: 3306      │◄─┤  Port: 8080      │  │ │  │
│  │  │  │  DB: electronicstore│ │                  │  │ │  │
│  │  │  └──────────────────┘  └──────────────────┘  │ │  │
│  │  └────────────────────────────────────────────────┘ │  │
│  │                                                      │  │
│  │  Public IP: 130.107.145.195                          │  │
│  │  Inbound Port: 8080 (Open)                           │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                        │
                        │ Access
                        ▼
┌─────────────────────────────────────────────────────────────┐
│                    END USERS                                │
│  http://130.107.145.195:8080                                │
│  http://130.107.145.195:8080/swagger-ui.html                │
└─────────────────────────────────────────────────────────────┘
```

---

## ✅ Prerequisites

### Required Tools
- [x] Docker Desktop installed
- [x] Docker Hub account
- [x] Azure account with active subscription
- [x] SSH client (Terminal/PuTTY)
- [x] Maven (for building the application)

### Required Knowledge
- Basic Docker commands
- SSH connection
- Linux command line basics

---

## 📦 Phase 1: Dockerizing the Application

### Step 1.1: Understanding Dockerfile Basics

**Common Dockerfile Commands:**

```dockerfile
FROM openjdk:21-jdk-slim          # Defines the base image
WORKDIR /usr/src/myapp            # Creates target folder with this path
COPY . /usr/src/myapp/            # Copy all from current folder to target folder
RUN javac Test.java               # Execute commands during build
CMD ["java", "filename"]          # Set default executable for container
EXPOSE 9898                       # Document which port the container listens on
```

### Step 1.2: Create Dockerfile

Create `Dockerfile` in the project root:

```dockerfile
FROM openjdk:21-jdk-slim
WORKDIR /usr/src/myapp
COPY . /usr/src/myapp/
CMD ["java", "-jar", "electronic-store-backend-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
```

**Screenshot Reference:** `screenshots/01-dockerfile-created.png`

### Step 1.3: Create docker-compose.yml

Create `docker-compose.yml` with all dependencies:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: electronic-store-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: electronicstore
      MYSQL_USER: appuser
      MYSQL_PASSWORD: apppass
    ports:
      - "3309:3306"
    networks:
      - electronic-store-network
    command: --default-authentication-plugin=mysql_native_password

  app:
    build: .
    container_name: electronic-store-app
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://electronic-store-mysql:3306/electronicstore
      SPRING_DATASOURCE_USERNAME: appuser
      SPRING_DATASOURCE_PASSWORD: apppass
    depends_on:
      - mysql
    networks:
      - electronic-store-network

networks:
  electronic-store-network:
    driver: bridge
```

**Screenshot Reference:** `screenshots/02-docker-compose-created.png`

### Step 1.4: Build the Application

```bash
# Navigate to project directory
cd springBasicSamples/electronicStorePorject/electronic-store-backend

# Build the application
./mvnw clean package -DskipTests

# Verify JAR file is created
ls -lh target/*.jar
```

**Expected Output:**
```
-rw-r--r--  1 user  staff   45M Jan 15 10:30 electronic-store-backend-0.0.1-SNAPSHOT.jar
```

**Screenshot Reference:** `screenshots/03-maven-build-success.png`

### Step 1.5: Build Docker Image

```bash
# Build the Docker image
docker build -t electronic-store-app .

# Verify image is created
docker images | grep electronic-store
```

**Expected Output:**
```
electronic-store-app    latest    abc123def456    2 minutes ago    500MB
```

**Screenshot Reference:** `screenshots/04-docker-image-built.png`

### Step 1.6: Test Locally (Optional)

```bash
# Start all services
docker-compose up -d

# Check running containers
docker ps

# Test the application
curl http://localhost:8080/actuator/health
```

---

## 🌐 Phase 2: Publishing to Docker Hub

### Step 2.1: Login to Docker Hub

```bash
# Login to Docker Hub
docker login

# Enter your credentials
Username: taalib1705
Password: ********
```

**Screenshot Reference:** `screenshots/05-docker-hub-login.png`

### Step 2.2: Tag the Image

```bash
# Tag the image for Docker Hub
docker tag electronic-store-app taalib1705/electronicstore1.0

# For bundled version with DB
docker tag demo-of-electronic-store-app taalib1705/electronicstorefull1.0
```

**Screenshot Reference:** `screenshots/06-docker-tag-image.png`

### Step 2.3: Push to Docker Hub

```bash
# Push the image
docker push taalib1705/electronicstore1.0

# Push bundled version
docker push taalib1705/electronicstorefull1.0
```

**Expected Output:**
```
The push refers to repository [docker.io/taalib1705/electronicstore1.0]
abc123def456: Pushed
latest: digest: sha256:... size: 2345
```

**Screenshot Reference:** `screenshots/07-docker-push-success.png`

### Step 2.4: Verify on Docker Hub

**Docker Hub Repository Links:**
- Application Image: https://hub.docker.com/repository/docker/taalib1705/electronicstore1.0/general
- Bundled Version: https://hub.docker.com/repository/docker/taalib1705/electronicstorefull1.0/general

**Screenshot Reference:** `screenshots/08-docker-hub-repository.png`

### Step 2.5: Build for Linux Platform (If needed)

If you encounter platform compatibility issues on Azure VM:

```bash
# Build for Linux AMD64 platform
docker buildx build --platform linux/amd64 \
  -t taalib1705/electronicstorefull1.0:latest \
  --push .
```

**Screenshot Reference:** `screenshots/09-docker-buildx-platform.png`

---

## ☁️ Phase 3: Azure VM Setup

### Step 3.1: Create Resource Group

**Azure Portal Steps:**
1. Login to Azure Portal (https://portal.azure.com)
2. Click "Resource groups"
3. Click "+ Create"
4. Fill in details:
   - Resource group name: `electronic-store-rg`
   - Region: `East US`
5. Click "Review + Create"

**Screenshot Reference:** `screenshots/10-azure-resource-group.png`

### Step 3.2: Create Virtual Machine

**Azure Portal Steps:**
1. Click "Virtual machines"
2. Click "+ Create" → "Azure virtual machine"
3. Fill in details:
   - Resource group: `electronic-store-rg`
   - VM name: `electronic-store-vm`
   - Region: `East US`
   - Image: `Ubuntu Server 20.04 LTS`
   - Size: `Standard_B2s` (2 vCPUs, 4 GB RAM)
   - Authentication: SSH public key
   - Username: `azureuser`
4. Configure networking:
   - Allow SSH (22)
   - Allow HTTP (80)
   - Allow Custom (8080)
5. Click "Review + Create"

**Screenshot Reference:** `screenshots/11-azure-vm-created.png`

### Step 3.3: Connect to VM via SSH

**Get VM Public IP:**
- Navigate to VM in Azure Portal
- Copy Public IP address: `130.107.145.195`

**Screenshot Reference:** `screenshots/12-azure-vm-ip.png`

**Connect via SSH:**
```bash
# Connect to Azure VM
ssh azureuser@130.107.145.195

# Accept fingerprint
Are you sure you want to continue connecting (yes/no)? yes
```

**Screenshot Reference:** `screenshots/13-ssh-connection.png`

### Step 3.4: Install Docker on VM

```bash
# Update package list
sudo apt-get update

# Install Docker
sudo apt-get install -y docker.io

# Start Docker service
sudo systemctl start docker
sudo systemctl enable docker

# Add user to docker group
sudo usermod -aG docker $USER

# Verify installation
docker --version
```

**Expected Output:**
```
Docker version 20.10.12, build e91ed57
```

**Screenshot Reference:** `screenshots/14-docker-installed-vm.png`

### Step 3.5: Create Docker Network

```bash
# Create custom bridge network
docker network create electronic-store-network

# Verify network
docker network ls
```

**Screenshot Reference:** `screenshots/15-docker-network-created.png`

---

## 🚀 Phase 4: Running on Azure VM

### Step 4.1: Pull Images from Docker Hub

```bash
# Pull the application image
docker pull taalib1705/electronicstore1.0

# Pull MySQL image
docker pull mysql:8.0

# Verify images
docker images
```

**Screenshot Reference:** `screenshots/16-docker-images-pulled.png`

### Step 4.2: Start MySQL Container

```bash
# Run MySQL container
docker run -d \
  --name electronic-store-mysql \
  --network electronic-store-network \
  -p 3309:3306 \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=electronicstore \
  -e MYSQL_USER=appuser \
  -e MYSQL_PASSWORD=apppass \
  mysql:8.0 \
  --default-authentication-plugin=mysql_native_password

# Check container status
docker ps
```

**Expected Output:**
```
CONTAINER ID   IMAGE       COMMAND                  STATUS         PORTS                    NAMES
abc123def456   mysql:8.0   "docker-entrypoint.s…"   Up 10 seconds  0.0.0.0:3309->3306/tcp   electronic-store-mysql
```

**Screenshot Reference:** `screenshots/17-mysql-container-running.png`

### Step 4.3: Verify MySQL Container

```bash
# Check MySQL logs
docker logs electronic-store-mysql

# Connect to MySQL (optional)
docker exec -it electronic-store-mysql mysql -u appuser -p
# Password: apppass

# Show databases
SHOW DATABASES;
```

**Screenshot Reference:** `screenshots/18-mysql-database-verified.png`

### Step 4.4: Start Application Container

```bash
# Run application container
docker run -d \
  --name electronic-store-app \
  --network electronic-store-network \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://electronic-store-mysql:3306/electronicstore \
  -e SPRING_DATASOURCE_USERNAME=appuser \
  -e SPRING_DATASOURCE_PASSWORD=apppass \
  taalib1705/electronicstore1.0

# Check container status
docker ps
```

**Expected Output:**
```
CONTAINER ID   IMAGE                              STATUS         PORTS                    NAMES
def456ghi789   taalib1705/electronicstore1.0      Up 5 seconds   0.0.0.0:8080->8080/tcp   electronic-store-app
abc123def456   mysql:8.0                          Up 2 minutes   0.0.0.0:3309->3306/tcp   electronic-store-mysql
```

**Screenshot Reference:** `screenshots/19-app-container-running.png`

### Step 4.5: Check Application Logs

```bash
# View application logs
docker logs electronic-store-app

# Follow logs in real-time
docker logs -f electronic-store-app
```

**Look for:**
```
Started ElectronicStoreApplication in X.XXX seconds
```

**Screenshot Reference:** `screenshots/20-app-logs-started.png`

---

## 🌐 Phase 5: Network Configuration

### Step 5.1: Check VM Network Settings

**Azure Portal Steps:**
1. Navigate to your VM
2. Click "Networking" in left menu
3. Review existing inbound port rules

**Screenshot Reference:** `screenshots/21-azure-vm-networking.png`

### Step 5.2: Add Inbound Port Rule for 8080

**Azure Portal Steps:**
1. Click "Add inbound port rule"
2. Configure:
   - Source: `Any`
   - Source port ranges: `*`
   - Destination: `Any`
   - Service: `Custom`
   - Destination port ranges: `8080`
   - Protocol: `TCP`
   - Action: `Allow`
   - Priority: `1010`
   - Name: `Allow-HTTP-8080`
3. Click "Add"

**Screenshot Reference:** `screenshots/22-inbound-port-8080-added.png`

### Step 5.3: Verify Port is Open

```bash
# On Azure VM, check if port is listening
sudo netstat -tulpn | grep 8080

# Or using ss command
sudo ss -tulpn | grep 8080
```

**Expected Output:**
```
tcp6       0      0 :::8080                 :::*                    LISTEN      12345/docker-proxy
```

**Screenshot Reference:** `screenshots/23-port-8080-listening.png`

---

## ✅ Phase 6: Testing & Verification

### Step 6.1: Access Application Homepage

**Open in Browser:**
```
http://130.107.145.195:8080
```

**Screenshot Reference:** `screenshots/24-application-homepage.png`

### Step 6.2: Access Swagger UI

**Open in Browser:**
```
http://130.107.145.195:8080/swagger-ui.html
```

**What You Should See:**
- Complete API documentation
- All endpoints listed
- Try it out functionality

**Screenshot Reference:** `screenshots/25-swagger-ui-interface.png`

### Step 6.3: Test Health Endpoint

```bash
# From your local machine
curl http://130.107.145.195:8080/actuator/health
```

**Expected Response:**
```json
{
  "status": "UP"
}
```

**Screenshot Reference:** `screenshots/26-health-check-success.png`

### Step 6.4: Test Authentication - Generate Token

**Using Swagger UI or Postman:**

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "email": "admin@gmail.com",
  "password": "admin123"
}
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "1",
    "name": "Admin User",
    "email": "admin@gmail.com",
    "roles": ["ADMIN"]
  }
}
```

**Screenshot Reference:** `screenshots/27-token-generation-success.png`

### Step 6.5: Test Protected Endpoints

**Using the JWT Token:**

**Endpoint:** `GET /api/products`

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Screenshot Reference:** `screenshots/28-protected-endpoint-test.png`

### Step 6.6: Test User Registration

**Endpoint:** `POST /api/users/register`

**Request Body:**
```json
{
  "name": "Test User",
  "email": "test@example.com",
  "password": "password123",
  "gender": "Male",
  "about": "Test user account"
}
```

**Screenshot Reference:** `screenshots/29-user-registration-test.png`

---

## 🐛 Troubleshooting

### Issue 1: Platform Compatibility Error

**Symptom:**
```
WARNING: The requested image's platform (linux/arm64) does not match the detected host platform (linux/amd64)
```

**Solution:**
Rebuild the image for the correct platform:

```bash
docker buildx build --platform linux/amd64 \
  -t taalib1705/electronicstorefull1.0:latest \
  --push .
```

**Screenshot Reference:** `screenshots/30-platform-issue-resolved.png`

### Issue 2: Container Won't Start

**Check Logs:**
```bash
docker logs electronic-store-app
```

**Common Causes:**
- Database connection failed
- Port already in use
- Environment variables incorrect

**Solution:**
```bash
# Remove and recreate container
docker rm -f electronic-store-app

# Run with correct parameters
docker run -d \
  --name electronic-store-app \
  --network electronic-store-network \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://electronic-store-mysql:3306/electronicstore \
  -e SPRING_DATASOURCE_USERNAME=appuser \
  -e SPRING_DATASOURCE_PASSWORD=apppass \
  taalib1705/electronicstore1.0
```

### Issue 3: Cannot Access Application from Browser

**Check:**
1. Container is running: `docker ps`
2. Port is open in Azure: Check inbound rules
3. Application started: `docker logs electronic-store-app`

**Solution:**
```bash
# Restart container
docker restart electronic-store-app

# Check Azure firewall rules
# Ensure port 8080 is allowed
```

### Issue 4: Database Connection Failed

**Check MySQL Container:**
```bash
# Check if MySQL is running
docker ps | grep mysql

# Check MySQL logs
docker logs electronic-store-mysql

# Test connection
docker exec -it electronic-store-mysql mysql -u appuser -p
```

**Solution:**
```bash
# Restart MySQL container
docker restart electronic-store-mysql

# Wait 30 seconds, then restart app
docker restart electronic-store-app
```

---

## 📝 Useful Commands

### Docker Container Management

```bash
# List running containers
docker ps

# List all containers (including stopped)
docker ps -a

# View container logs
docker logs <container-name>

# Follow logs in real-time
docker logs -f <container-name>

# Stop container
docker stop <container-name>

# Start container
docker start <container-name>

# Restart container
docker restart <container-name>

# Remove container
docker rm <container-name>

# Remove container forcefully
docker rm -f <container-name>

# Execute command in container
docker exec -it <container-name> bash

# View container stats
docker stats
```

### Docker Image Management

```bash
# List images
docker images

# Pull image
docker pull <image-name>

# Remove image
docker rmi <image-name>

# Tag image
docker tag <source-image> <target-image>

# Push image
docker push <image-name>

# Build image
docker build -t <image-name> .
```

### Docker Network Management

```bash
# List networks
docker network ls

# Create network
docker network create <network-name>

# Inspect network
docker network inspect <network-name>

# Remove network
docker network rm <network-name>

# Connect container to network
docker network connect <network-name> <container-name>
```

### Azure VM Management

```bash
# Connect via SSH
ssh azureuser@130.107.145.195

# Check system resources
htop
# or
top

# Check disk usage
df -h

# Check memory usage
free -h

# Check running processes
ps aux | grep java

# Check open ports
sudo netstat -tulpn
```

### Application Management

```bash
# Check application health
curl http://130.107.145.195:8080/actuator/health

# Check application info
curl http://130.107.145.195:8080/actuator/info

# View application metrics
curl http://130.107.145.195:8080/actuator/metrics

# Test API endpoint
curl -X GET http://130.107.145.195:8080/api/products
```

---

## 🔄 Updating the Application

### Step 1: Build New Version Locally

```bash
# Make code changes
# Build new version
./mvnw clean package -DskipTests

# Build new Docker image
docker build -t electronic-store-app:v2.0 .

# Tag for Docker Hub
docker tag electronic-store-app:v2.0 taalib1705/electronicstore1.0:v2.0

# Push to Docker Hub
docker push taalib1705/electronicstore1.0:v2.0
```

### Step 2: Update on Azure VM

```bash
# SSH to Azure VM
ssh azureuser@130.107.145.195

# Pull new image
docker pull taalib1705/electronicstore1.0:v2.0

# Stop and remove old container
docker stop electronic-store-app
docker rm electronic-store-app

# Run new version
docker run -d \
  --name electronic-store-app \
  --network electronic-store-network \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://electronic-store-mysql:3306/electronicstore \
  -e SPRING_DATASOURCE_USERNAME=appuser \
  -e SPRING_DATASOURCE_PASSWORD=apppass \
  taalib1705/electronicstore1.0:v2.0

# Verify
docker ps
docker logs -f electronic-store-app
```

---

## 📊 Monitoring

### Check Container Health

```bash
# View container stats
docker stats

# Check resource usage
docker stats electronic-store-app electronic-store-mysql
```

### View Application Logs

```bash
# Last 100 lines
docker logs --tail 100 electronic-store-app

# Follow logs
docker logs -f electronic-store-app

# Logs with timestamps
docker logs -t electronic-store-app
```

### Database Monitoring

```bash
# Connect to MySQL
docker exec -it electronic-store-mysql mysql -u appuser -p

# Check database size
SELECT 
  table_schema AS 'Database',
  ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)'
FROM information_schema.tables
GROUP BY table_schema;

# Check active connections
SHOW PROCESSLIST;
```

---

## 🎉 Deployment Summary

### ✅ What We Accomplished

1. **Dockerized** the Spring Boot application
2. **Published** Docker images to Docker Hub
3. **Created** Azure Virtual Machine
4. **Deployed** MySQL and Application containers
5. **Configured** networking and security
6. **Tested** all endpoints successfully

### 📍 Deployment Details

| Component | Details |
|-----------|---------|
| **Application URL** | http://130.107.145.195:8080 |
| **Swagger UI** | http://130.107.145.195:8080/swagger-ui.html |
| **Docker Hub** | taalib1705/electronicstore1.0 |
| **Azure VM** | Standard_B2s (2 vCPUs, 4 GB RAM) |
| **Database** | MySQL 8.0 (Container) |
| **Network** | electronic-store-network (Bridge) |

### 🔑 Default Credentials

**Admin User:**
- Email: `admin@gmail.com`
- Password: `admin123`

**Database:**
- Host: `electronic-store-mysql` (internal)
- Port: `3306`
- Database: `electronicstore`
- Username: `appuser`
- Password: `apppass`

---

## 📸 Screenshots Directory

All screenshots referenced in this document should be placed in:
```
electronic-store-backend/deployment-screenshots/
```

**Screenshot List:**
1. `01-dockerfile-created.png` - Dockerfile content
2. `02-docker-compose-created.png` - docker-compose.yml
3. `03-maven-build-success.png` - Maven build output
4. `04-docker-image-built.png` - Docker image created
5. `05-docker-hub-login.png` - Docker Hub login
6. `06-docker-tag-image.png` - Image tagging
7. `07-docker-push-success.png` - Push to Docker Hub
8. `08-docker-hub-repository.png` - Docker Hub repository
9. `09-docker-buildx-platform.png` - Platform-specific build
10. `10-azure-resource-group.png` - Azure resource group
11. `11-azure-vm-created.png` - Azure VM overview
12. `12-azure-vm-ip.png` - VM public IP
13. `13-ssh-connection.png` - SSH connection
14. `14-docker-installed-vm.png` - Docker installed on VM
15. `15-docker-network-created.png` - Docker network
16. `16-docker-images-pulled.png` - Images pulled
17. `17-mysql-container-running.png` - MySQL container
18. `18-mysql-database-verified.png` - Database verified
19. `19-app-container-running.png` - App container running
20. `20-app-logs-started.png` - Application started
21. `21-azure-vm-networking.png` - VM networking settings
22. `22-inbound-port-8080-added.png` - Port 8080 rule
23. `23-port-8080-listening.png` - Port listening
24. `24-application-homepage.png` - Application homepage
25. `25-swagger-ui-interface.png` - Swagger UI
26. `26-health-check-success.png` - Health check
27. `27-token-generation-success.png` - JWT token generated
28. `28-protected-endpoint-test.png` - Protected endpoint
29. `29-user-registration-test.png` - User registration
30. `30-platform-issue-resolved.png` - Platform fix

---

## 🎓 Key Learnings

1. **Docker Networking** - Containers communicate via custom bridge network
2. **Platform Compatibility** - Build images for target platform (linux/amd64)
3. **Azure VM Setup** - Configure inbound ports for application access
4. **Container Orchestration** - Start database before application
5. **Environment Variables** - Pass configuration via Docker run command

---

## 🚀 Next Steps

- [ ] Set up automated backups for MySQL
- [ ] Implement CI/CD pipeline
- [ ] Add monitoring with Azure Monitor
- [ ] Configure custom domain
- [ ] Set up SSL/TLS certificate
- [ ] Implement log aggregation
- [ ] Add container health checks
- [ ] Set up auto-restart policies

---

**Successfully Deployed! 🎉**

*Last Updated: January 2025*
