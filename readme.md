
# Demo User Management API

Java web application based on Spring Boot for user management with REST API interface.

## Description

The application provides a REST API for working with users, including capabilities for creating, retrieving a list of all users, and filtering by age. Data is stored in an in-memory H2 database.

## Technologies

- **Java 21**
- **Spring Boot 3.3.2**
- **Spring Web** - for creating REST API
- **Spring Data JPA** - for database interaction
- **H2 Database** - in-memory database
- **Lombok** - for code simplification
- **Maven** - build system

## Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── DemoApplication.java          # Main class
│   │   ├── controller/
│   │   │   └── UserController.java       # REST controller
│   │   ├── entity/
│   │   │   ├── User.java                 # User entity
│   │   │   └── Country.java              # Enum of countires
│   │   ├── repository/
│   │   │   └── UserRepository.java       # JPA repository
│   │   └── service/
│   │       └── UserService.java          # Service layer
│   └── resources/
│       ├── application.yml               # App configuration
│       ├── application.properties        # Properties file
│       └── data.sql                      # SQL script with initial data
└── test/
    └── java/com/example/demo/
        └── DemoApplicationTests.java     # Tests
```

## Data Model

### User
- `id` (Long) - unique identifier
- `firstName` (String) - user's first name
- `age` (Integer) - age
- `country` (Country) - country (Enum)

### Country (Enum)
Available countries: RUSSIA, BELARUS, ARMENIA, CHINA, USA

## API Endpoints

### 1. Get all users
```http
GET /user-api/v1/users
```

**Response:**
```json
[
  {
    "id": 1,
    "firstName": "Kirill",
    "age": 20,
    "country": "BELARUS"
  },
  {
    "id": 2,
    "firstName": "Ivan",
    "age": 21,
    "country": "RUSSIA"
  },
  {
    "id": 3,
    "firstName": "Chen",
    "age": 25,
    "country": "CHINA"
  },
  {
    "id": 4,
    "firstName": "Vahe",
    "age": 22,
    "country": "ARMENIA"
  },
  {
    "id": 5,
    "firstName": "George",
    "age": 30,
    "country": "USA"
  }
]
```

### 2. Create a new user
```http
POST /user-api/v1/users
Content-Type: application/json

{
  "firstName": "Alex",
  "age": 25,
  "country": "RUSSIA"
}
```

**Response:**
```json
{
  "id": 6,
  "firstName": "Alex",
  "age": 25,
  "country": "RUSSIA"
}
```

### 3. Get users older than specified age
```http
GET /user-api/v1/additional-info?age=23
```

Returns a list of users with age >= specified value, sorted by first name in alphabetical order.

**Response:**
```json
[
  {
    "id": 3,
    "firstName": "Chen",
    "age": 25,
    "country": "CHINA"
  },
  {
    "id": 5,
    "firstName": "George",
    "age": 30,
    "country": "USA"
  }
]
```

## Running the Application

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Local Setup

1. **Clone the repository:**
```bash
git clone <repository-url>
cd demo
```

2. **Build the project:**
```bash
./mvnw clean package
```

3. **Run the application:**
```bash
./mvnw spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

4. **Verify the application:**
The application will be available at: `http://localhost:8080`


### Using other REST clients
For testing you can also use:
- Postman
- IntelliJ IDEA HTTP Client
- Insomnia

## Database

The application uses an embedded H2 database in in-memory mode.

### H2 Console
H2 Console is available at: `http://localhost:8080/h2-console`

**Connection parameters:**
- JDBC URL: `jdbc:h2:mem:users_db`
- User Name: `sa`
- Password: (leave empty)

### Initial Data
On startup, the application automatically creates the `users` table and populates it with test data from the `data.sql` file:

| ID | First Name | Age | Country |
|----|------------|-----|---------|
| 1  | Kirill     | 20  | BELARUS |
| 2  | Ivan       | 21  | RUSSIA  |
| 3  | Chen       | 25  | CHINA   |
| 4  | Vahe       | 22  | ARMENIA |
| 5  | George     | 30  | USA     |

## Architecture

The application follows a three-layer architecture pattern:

1. **Controller Layer** - REST controllers for handling HTTP requests
2. **Service Layer** - business logic of the application
3. **Repository Layer** - data access layer (JPA)

### Used Spring annotations:
- `@RestController` - for REST controllers
- `@Service` - for service layer
- `@Repository` - for repositories
- `@Entity` - for JPA entities
- `@Transactional` - for transaction management

## Configuration

Main application settings are located in `application.yml`:

```yaml
spring:
  datasource:
    driver-class-name: org.h2.Driver
    generate-unique-name: false
    name: users_db
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: create-drop
    defer-datasource-initialization: true
```

## Development

### Run tests
```bash
./mvnw test
```

## Possible Improvements

- Adding input data validation
- Implementing error handling
- Adding logging
- Test coverage
- Adding UPDATE and DELETE operations
