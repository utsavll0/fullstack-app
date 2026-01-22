# Fullstack Cloud Monitoring Dashboard

A full-stack application built with **Spring Boot** (backend) and **Angular** (frontend) for monitoring cloud resources. The application uses **SQLite** as the database and includes basic authentication with username/password stored in the database.

## Features

- ✅ **Spring Boot Backend** with RESTful APIs
- ✅ **Angular Frontend** with modern UI
- ✅ **SQLite Database** for data persistence
- ✅ **Authentication System** with Spring Security
- ✅ **Cloud Resource Monitoring** Dashboard
- ✅ **CRUD Operations** for cloud resources
- ✅ **Sample Data** pre-populated in the database
- ✅ **Responsive Design** with modern styling

## Screenshots

### Login Page
![Login Page](https://github.com/user-attachments/assets/b43c7098-c747-4f5b-b9d6-4b0eef60314f)

### Dashboard
![Dashboard](https://github.com/user-attachments/assets/5aff8e58-118a-4ac9-a6b8-8752752a7bad)

## Tech Stack

### Backend
- Spring Boot 3.1.5
- Spring Data JPA
- Spring Security
- SQLite Database
- Java 17
- Maven

### Frontend
- Angular 17
- TypeScript
- RxJS
- HTTP Client

## Prerequisites

- Java 17 or higher
- Node.js 18+ and npm
- Maven 3.6+

## Setup and Installation

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Build the project:
```bash
mvn clean package
```

3. Run the application:
```bash
java -jar target/cloud-monitor-backend-1.0.0.jar
```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
ng serve
```

The frontend will start on `http://localhost:4200`

## Demo Credentials

The application comes with pre-populated sample users:

- **Admin User:**
  - Username: `admin`
  - Password: `admin123`

- **Regular User:**
  - Username: `user`
  - Password: `user123`

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout
- `GET /api/auth/status` - Check authentication status

### Cloud Resources
- `GET /api/resources` - Get all cloud resources
- `GET /api/resources/{id}` - Get resource by ID
- `POST /api/resources` - Create new resource
- `PUT /api/resources/{id}` - Update resource
- `DELETE /api/resources/{id}` - Delete resource

## Testing the API

```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Get all resources (using basic auth)
curl -X GET http://localhost:8080/api/resources \
  -u admin:admin123
```

## License

This project is open source and available for educational purposes.
