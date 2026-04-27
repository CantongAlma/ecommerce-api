Ecommerce API Project
 
 Project Overview
 
This is a RESTful API built with Spring Boot for managing products in an e-commerce system. It supports standard CRUD operations, input validation, and filtering. Data is stored in memory, meaning it resets when the server restarts.
 
 
Setup Instructions
 
Prerequisites
 
- Java Development Kit (JDK) 17 or higher
​
- Maven or Gradle build tool
​
- IDE like VS Code or IntelliJ
 
How to Run
 
1. Open a terminal in the project root folder.
​
2. Run the application using: 
./mvnw spring-boot:run
 or
 mvn spring-boot-run
​
3. The server will start at  http://localhost:8080 
 
 
 API Endpoints Reference
 
Table
   
Method Endpoint Description Request Body Response Status 
GET  /api/products  Get all products None 200 OK 
GET  /api/products/{id}  Get product by ID None 200 OK / 404 Not Found 
POST  /api/products  Create new product JSON Product 201 Created 
PUT  /api/products/{id}  Update full product JSON Product 200 OK / 404 Not Found 
DELETE  /api/products/{id}  Delete product None 204 No Content 
GET  /api/products/filter  Filter products None 200 OK 
 
 
  Sample Requests & Responses
  Create Product (POST)
 
URL:  http://localhost:8080/api/products 
 
Request Body:
{
  "name": "Wireless Mouse",
  "description": "Ergonomic design",
  "price": 850.50,
  "category": "Electronics",
  "stockQuantity": 30,
  "imageUrl": "mouse.jpg"
}
 
 
 
Response (201 Created):
{
  "id": 11,
  "name": "Wireless Mouse",
  "description": "Ergonomic design",
  "price": 850.50,
  "category": "Electronics",
  "stockQuantity": 30,
  "imageUrl": "mouse.jpg"
}
 
 Validation Error Example
 
Request Body:
{
  "name": "A",
  "price": -50,
  "category": ""
}
 
 Response (400 Bad Request):
{
  "timestamp": "2026-04-27T...",
  "statusCode": 400,
  "error": "Validation Failed",
  "message": "Price must be a positive number"
}
 
  Known Limitations
 
- In-Memory Storage: All data is stored temporarily in the computer's memory.
​
- When you stop the server or restart the application, all data will be lost and reset to the original list.
​
- No database is connected yet.