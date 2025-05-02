# GOEVENTS

**GOEvents** is an andoroid application for creating, managing, and discovering events in your city. It features a Kotlin Android frontend (in progress) and a functional Spring Boot + MongoDB REST API backend with JWT-based authentication.


## TECH STACK

**Frontend:** Kotlin (Android) – in development

**Backend:** Spring Boot

**Database:** MongoDB

**API:** REST

**Authentication:** JWT (JSON Web Tokens)


## FEATURES

JWT-based login & role-based access

Create, update, and delete events

Search & filter events by title, date, location, and type

User accounts: organizers & attendees

Kotlin Android frontend (in development)


## AUTHENTICATION

The backend uses JWT (JSON Web Tokens) for secure authentication and authorization. Upon login, the user receives a JWT which must be included in the Authorization header for protected routes.

### Example:

Authorization: Bearer `<your-jwt-token>`

**Auth Endpoints:**

POST /auth/register – Register a new user

POST /auth/login – Authenticate and receive JWT


Protected endpoints like POST /events, DELETE /events/{id} require a valid token


## BACKEND (Spring Boot + MongoDB)

REST API with CRUD endpoints

Filtering via query parameters

MongoDB for NoSQL flexibility

Secure access via JWT


  
### Feel free to use, improve or share!
