# Banking Users Service

Microservice for banking system user management.

## 🚀 Features
- Reactive REST API using Spring WebFlux
- MongoDB integration for data persistence
- Complete CRUD operations for user management
- Input validation and error handling
- Swagger/OpenAPI documentation
- Unit testing with JUnit 5 and WebTestClient

## Technologies Used
| Technology         |
|--------------------|
| ![Java](https://img.shields.io/badge/Java-21-007396?logo=java&logoColor=white)               |
| ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-6DB33F?logo=spring-boot&logoColor=white)        |
| ![Spring WebFlux](https://img.shields.io/badge/Spring%20WebFlux-3.3.3-6DB33F?logo=spring&logoColor=white)    |
| ![Maven](https://img.shields.io/badge/Maven-3.6.3-C71A36?logo=apache-maven&logoColor=white)              |
| ![Docker](https://img.shields.io/badge/Docker-20.10.7-2496ED?logo=docker&logoColor=white)              |


## Configuration

### 1. Environment Variables

Create a `.env` file in the project root:

```env
# Server Configuration
SERVER_PORT_USERS=8081
APPLICATION_NAME_USERS=banking-users-service
BASE_PATH_USERS=/banking-users-service

# MongoDB Configuration
MONGODB_HOST=mongodb
MONGODB_PORT=27017
MONGODB_DATABASE=banking
MONGODB_USERNAME=root
MONGODB_PASSWORD=example
MONGODB_AUTH_DATABASE=admin
MONGODB_AUTO_INDEX=true

# Logging Configuration
LOG_LEVEL_APP=DEBUG
LOG_LEVEL_MONGODB=DEBUG
LOG_LEVEL_WEB=DEBUG 
```

### 2. Application.yml Configuration

File location: `src/main/resources/application.yml`

```yaml
springdoc:
  swagger-ui:
    path: ${BASE_PATH_USERS:/swagger-ui.html}
  api-docs:
    path: ${BASE_PATH_USERS:/v3/api-docs}

server:
  port: ${SERVER_PORT_USERS:8081}

spring:
  application:
    name: ${APPLICATION_NAME:banking-users-service}
  data:
    mongodb:
      host: ${MONGODB_HOST:localhost}
      port: ${MONGODB_PORT:27017}
      database: ${MONGODB_DATABASE}
      username: ${MONGODB_USERNAME}
      password: ${MONGODB_PASSWORD}
      authentication-database: ${MONGODB_AUTH_DATABASE}
      auto-index-creation: ${MONGODB_AUTO_INDEX:true}
  webflux:
    base-path: ${BASE_PATH_USERS:/banking-users-service}

logging:
  level:
    io.banking.whatsapp: ${LOG_LEVEL_APP:DEBUG}
    org.springframework.data.mongodb:  ${LOG_LEVEL_MONGODB:DEBUG}
    org.springframework.web: TRACE
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
```

## Installation & Deployment

### Prerequisites
- Java 21
- Maven
- MongoDB

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/banking-users-service.git

2. Navigate to the project directory:
   ```bash
   cd C:\Workspaces\Proyectos\whatsAppBanking\banking-users-service

3. Build the project:
   ```bash
   mvn clean install

4. Run the application:
   ```bash
   mvn spring-boot:run

5. The service will be accessible at  The service will be accessible at URL_ADDRESS:8080. 

### API Endpoints
 Refer to the ***Banking_Users_Service.postman_collection.json*** file for detailed API endpoint information.

### Main Endpoints

1. **Create User**
   - URL: `/api/v1/users`
   - Method: POST
   - Swagger: [Create User](http://localhost:8081/banking-users-service/webjars/swagger-ui/index.html#/Users/createUser)

2. **Get User by ID**
   - URL: `/api/v1/users/{id}`
   - Method: GET
   - Swagger: [Get User](http://localhost:8081/banking-users-service/webjars/swagger-ui/index.html#/Users/getUserById)

3. **Get User by DNI**
   - URL: `/api/v1/users/dni/{dni}`
   - Method: GET
   - Swagger: [Get by DNI](http://localhost:8081/banking-users-service/webjars/swagger-ui/index.html#/Users/getUserByDni)

4. **Update User**
   - URL: `/api/v1/users/{id}`
   - Method: PUT
   - Swagger: [Update User](http://localhost:8081/banking-users-service/webjars/swagger-ui/index.html#/Users/updateUser)

5. **Delete User**
   - URL: `/api/v1/users/{id}`
   - Method: DELETE
   - Swagger: [Delete User](http://localhost:8081/banking-users-service/webjars/swagger-ui/index.html#/Users/deleteUser)

## Troubleshooting

1. **MongoDB Connection Issues:**
   - Verify credentials in `.env`
   - Check if MongoDB container is running
   - Review MongoDB logs: `docker-compose logs mongodb`

2. **Service Start Issues:**
   - Verify port 8081 is available
   - Check service logs: `docker-compose logs app`
   - Verify environment variables in `.env`

### Docker Deployment

1. **Start Services**
```bash
# Build and start services
docker-compose up -d

# View logs
docker-compose logs -f

# View specific service logs
docker-compose logs -f app
docker-compose logs -f mongodb
```

2. **Stop Services**
```bash
# Stop services
docker-compose down

# Stop services and remove volumes
docker-compose down -v
```

3. **Verify Deployment**
```bash
# Check running containers
docker-compose ps

# Test service health
curl http://localhost:8081/banking-users-service/actuator/health
```

## 📚 API Documentation

Access Swagger UI: http://localhost:8081/banking-users-service/webjars/swagger-ui/index.html#/Users/createUser


## 🧪 Running Tests

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=UserControllerTest
```



## Docker Hub

Service image is available on Docker Hub:
```bash
docker pull marceloalbarracin/banking-users-service:1.0.20
```

## Author
Marcelo Alejandro Albarracín
marceloalejandro.albarracin@gmail.com

## ¿Te gusta el contenido que comparto? Invítame un café para ayudarme a seguir creando. ¡Gracias por tu apoyo!
[![Buy Me a Coffee](https://img.shields.io/badge/Buy%20Me%20a%20Coffee-F7DF1E?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black)](https://buymeacoffee.com/malbarracin) 


