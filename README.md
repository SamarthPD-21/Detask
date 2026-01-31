# TaskFlow - Trello-like Task Management System

A full-stack task management application built with Spring Boot and Next.js, inspired by Trello.

## 🚀 Features

### Backend (Spring Boot)
- **JWT Authentication** - Secure user registration and login with access & refresh tokens
- **Board Management** - Create, update, delete, and share boards
- **List Management** - Create lists within boards, reorder with drag & drop
- **Card Management** - Full CRUD with descriptions, due dates, priorities, labels
- **Comments & Checklists** - Add comments and checklists to cards
- **File Attachments** - Upload files to cards (10MB limit)
- **Real-time Notifications** - In-app notifications for assignments and updates
- **Email Integration** - Welcome emails, invitations, due date reminders
- **Caching** - Caffeine cache for improved performance
- **Rate Limiting** - Bucket4j for API rate limiting (100 requests/minute)
- **Analytics** - User and board analytics with completion rates
- **Swagger Documentation** - Full API documentation at /swagger-ui.html

### Frontend (Next.js)
- **Modern UI** - Clean, Trello-inspired design
- **Drag & Drop** - Move cards between lists
- **Responsive** - Works on desktop and mobile
- **Real-time Updates** - Optimistic UI updates
- **Authentication** - Login/Register with JWT
- **Board Views** - Grid layout for boards, Kanban for tasks

## 📁 Project Structure

```
springy/
├── server/                 # Spring Boot Backend
│   ├── src/main/java/com/taskflow/server/
│   │   ├── config/         # Configuration classes
│   │   ├── controller/     # REST Controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Custom exceptions
│   │   ├── model/          # MongoDB entities
│   │   ├── repository/     # Data repositories
│   │   ├── security/       # JWT & Security
│   │   ├── service/        # Business logic
│   │   └── util/           # Utility classes
│   └── src/main/resources/
│       └── application.properties
│
└── client/                 # Next.js Frontend
    ├── app/                # Next.js App Router pages
    ├── components/         # React components
    ├── lib/                # API & Store
    ├── types/              # TypeScript types
    └── hooks/              # Custom hooks
```

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot 3.2.0
- Spring Security with JWT
- Spring Data MongoDB
- Caffeine Cache
- Bucket4j Rate Limiting
- SpringDoc OpenAPI (Swagger)
- Lombok

### Frontend
- Next.js 16
- React 19
- TypeScript
- Tailwind CSS
- Zustand (State Management)
- Axios
- Lucide Icons

## 📦 Prerequisites

- Java 21+
- Node.js 18+
- MongoDB 6+
- Maven 3.9+

## 🚀 Getting Started

### 1. Start MongoDB

```bash
# Using Docker
docker run -d -p 27017:27017 --name mongodb mongo:latest

# Or install locally
mongod --dbpath /path/to/data
```

### 2. Start the Backend

```bash
cd server

# Configure application.properties if needed
# Update MongoDB URI, JWT secret, email settings

# Run with Maven
./mvnw spring-boot:run

# Or build and run JAR
./mvnw clean package
java -jar target/taskflow-1.0.0.jar
```

The backend will start at `http://localhost:8080`

### 3. Start the Frontend

```bash
cd client

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will start at `http://localhost:3000`

## 📚 API Documentation

Once the backend is running, visit:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

### Key Endpoints

#### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `POST /api/auth/refresh` - Refresh token
- `GET /api/auth/me` - Get current user

#### Boards
- `GET /api/boards` - Get user's boards
- `POST /api/boards` - Create board
- `GET /api/boards/{id}` - Get board
- `GET /api/boards/{id}/detail` - Get board with lists & cards
- `PUT /api/boards/{id}` - Update board
- `DELETE /api/boards/{id}` - Delete board

#### Lists
- `POST /api/boards/{boardId}/lists` - Create list
- `PUT /api/lists/{id}` - Update list
- `DELETE /api/lists/{id}` - Delete list

#### Cards
- `POST /api/lists/{listId}/cards` - Create card
- `GET /api/cards/{id}` - Get card
- `PUT /api/cards/{id}` - Update card
- `POST /api/cards/{id}/move` - Move card
- `DELETE /api/cards/{id}` - Delete card

## 🔧 Configuration

### Backend (application.properties)

```properties
# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/taskflow

# JWT
app.jwt.secret=your-secret-key
app.jwt.expiration=86400000
app.jwt.refresh-expiration=604800000

# Email (optional)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

# File Upload
app.upload.dir=./uploads
spring.servlet.multipart.max-file-size=10MB
```

### Frontend (.env.local)

```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

## 🧪 Testing

```bash
# Backend tests
cd server
./mvnw test

# Frontend tests
cd client
npm test
```

## 📝 License

This project is created for educational purposes as a Final Term Backend Engineering Project.

## 👤 Author

Created with ❤️ for Backend Engineering Final Term Project
