 Technologies & Dependencies

* **Java Version**: 17 or higher
* **Framework**: Spring Boot 3.x
* **Build Tool**: Maven
* **Key Dependencies**:
    * `spring-boot-starter-web` – For building RESTful services.
    * `spring-boot-starter-validation` – For robust input validation.
    * `lombok` – To reduce boilerplate code (e.g., getters, setters, constructors).
    * `spring-boot-devtools` – Provides development-time features like automatic restarts.

---

 Setup Instructions

 Prerequisites

Before running the application, ensure you have the following installed:

* **Java Development Kit (JDK) 17** or higher.
* **Maven** or Gradle build tool (Maven is used in this project).
* An Integrated Development Environment (IDE) like **VS Code** or **IntelliJ IDEA** is recommended.

 How to Run

1. **Clone the Repository:**
    Open your terminal or command prompt and run the following command:
    ```bash
    git clone https://github.com/[YOUR_USERNAME]/[YOUR_REPOSITORY_NAME].git
    ```
    *Replace `[YOUR_USERNAME]` with your GitHub username and `[YOUR_REPOSITORY_NAME]` with the actual name of your repository.*

2. **Navigate to the Project Directory:**
    ```bash
    cd [YOUR_REPOSITORY_NAME]
    ```

3. **Run the Application:**
    You can run the Spring Boot application using Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
    *(For Windows users, you might use `mvnw spring-boot:run`)*

4. **Access the API:**
    The server will start on `http://localhost:8080`.



 API Endpoints Reference

All API endpoints return JSON data.

| HTTP Method | Endpoint | Description | Request Body (if any) | Response Status |
| :---------- | :----------------------------- | :-------------------------------------------------- | :-------------------------------------------------- | :-------------- |
| `GET` | `/api/products` | Retrieve a list of all available products. | None | `200 OK` |
| `GET` | `/api/products/{id}` | Get a single product by its unique ID. | None | `200 OK` / `404 Not Found` |
| `GET` | `/api/products/filter` | Filter products based on specified criteria. | **URL Parameters:** `filterType`, `filterValue` | `200 OK` |
| `POST` | `/api/products` | Create and add a new product to the system. | `JSON Product Object` | `201 Created` / `400 Bad Request` |
| `PUT` | `/api/products/{id}` | Update all details of an existing product. | `JSON Product Object` (Full update) | `200 OK` / `404 Not Found` / `400 Bad Request` |
| `PATCH` | `/api/products/{id}` | Partially update specific fields of a product. | `JSON Product Object` (Partial update with fields to change) | `200 OK` / `404 Not Found` / `400 Bad Request` |
| `DELETE` | `/api/products/{id}` | Remove a product from the system using its ID. | None | `204 No Content` / `404 Not Found` |

---

 Sample Requests & Responses

 1. Create Product (`POST`)

* **URL:** `http://localhost:8080/api/products`
* **Method:** `POST`
* **Request Body:**
    ```json
    {
      "name": "Wireless Mouse",
      "description": "Ergonomic design with silent clicks",
      "price": 850.50,
      "category": "Electronics",
      "stockQuantity": 30,
      "imageUrl": "mouse.jpg"
    }
    ```
* **Response (201 Created):**
    ```json
    {
      "id": 11,
      "name": "Wireless Mouse",
      "description": "Ergonomic design with silent clicks",
      "price": 850.50,
      "category": "Electronics",
      "stockQuantity": 30,
      "imageUrl": "mouse.jpg"
    }
    ```

 2. Get All Products (`GET`)

* **URL:** `http://localhost:8080/api/products`
* **Method:** `GET`
* **Response (200 OK):**
    ```json
    [
      {
        "id": 1,
        "name": "Wireless Headphones",
        "description": "Noise-cancelling over-ear headphones",
        "price": 59.99,
        "category": "Electronics",
        "stockQuantity": 15,
        "imageUrl": "headphone.jpg"
      },
      {
        "id": 2,
        "name": "Mechanical Keyboard",
        "description": "RGB backlit gaming keyboard with tactile switches",
        "price": 24.99,
        "category": "Electronics",
        "stockQuantity": 20,
        "imageUrl": "keyboard.jpg"
      }
    ]
    ```

 3. Filter Products by Price Range (`GET`)

* **URL:** `http://localhost:8080/api/products/filter?filterType=price&filterValue=10,50`
* **Method:** `GET`
* **Response (200 OK):**
    ```json
    [
      {
        "id": 2,
        "name": "Mechanical Keyboard",
        "description": "RGB backlit gaming keyboard with tactile switches",
        "price": 24.99,
        "category": "Electronics",
        "stockQuantity": 20,
        "imageUrl": "keyboard.jpg"
      },
      {
        "id": 3,
        "name": "Cotton T-Shirt",
        "description": "100% organic cotton, comfortable fit",
        "price": 3.99,
        "category": "Clothing",
        "stockQuantity": 50,
        "imageUrl": "tshirt.jpg"
      }
    ]
    ```

 4. Validation Error Example (`400 Bad Request`)

* **Request Body:**
    ```json
    {
      "name": "A",
      "price": -50,
      "category": ""
    }
    ```
* **Response (400 Bad Request):**
    ```json
    {
      "timestamp": "2026-04-27T...",
      "statusCode": 400,
      "error": "Validation Failed",
      "messages": [
        "Product name length must be between 2 and 100 characters",
        "Price must be a positive number",
        "Category cannot be empty"
      ]
    }
    ```

 5. Not Found Error Example (`404 Not Found`)

* **URL:** `http://localhost:8080/api/products/99`
* **Method:** `GET`
* **Response (404 Not Found):**
    ```json
    {
      "timestamp": "2026-04-28T...",
      "status": 404,
      "error": "Not Found",
      "message": "Product with ID 99 not found",
      "path": "/api/products/99"
    }
    ```

---

 Known Limitations

* **In-Memory Storage**: All data is stored temporarily in the application's memory (`ArrayList` or similar structure). This means any changes (additions, updates, deletions) will be **lost** when the server is stopped or restarted.
* **No Database Integration**: The API currently does not connect to any persistent database.
* **No Authentication/Authorization**: User authentication and authorization mechanisms are not yet implemented.

EcommerceApi

## Database Schema

This project utilizes a MySQL database named ecommercedb. The primary entity for storing product information is Product, which is mapped to the products table in the database.

### products Table Structure:

The products table contains the following columns:

| Column Name   | Data Type         | Constraints                   | Description                                          |
| :------------ | :---------------- | :---------------------------- | :--------------------------------------------------- |
| id          | BIGINT          | PRIMARY KEY, AUTO_INCREMENT | A unique identifier for each product.                |
| name        | VARCHAR(255)    | NOT NULL                    | The name of the product.                             |
| description | VARCHAR(255)    | NULLABLE                    | A brief description of the product.                  |
| price       | DOUBLE          | NOT NULL                    | The selling price of the product.                    |
| category_id | INT             | NULLABLE                    | An integer representing the category of the product. |

---

## API Endpoints

The backend Spring Boot application exposes a RESTful API endpoint to retrieve product information.

### Products API

-   *Endpoint:* /api/products
-   *Method:* GET
-   *URL:* http://localhost:8080/api/products
-   *Description:* Retrieves a JSON array of all available products from the database.
-   *Example Response (JSON):*
    
    [
        {
            "id": 1,
            "name": "Wireless Headphones",
            "description": "High quality sound",
            "price": 19.99,
            "categoryId": 1
        },
        {
            "id": 2,
            "name": "Smart Watch",
            "description": "Fitness tracker",
            "price": 29.99,
            "categoryId": 1
        },
        {
            "id": 3,
            "name": "Shoes",
            "description": "Comfortable sport shoes",
            "price": 39.99,
            "categoryId": 2
        }
        // ... more products
    ]
    
    # Ecommerce API - Laboratory 9

## Testing the Authentication Endpoints

### 1. Register a new user
```bash
curl -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "cantongalma487@gmail.com",
    "password": "123456",
    "firstName": "Alma",
    "lastName": "Cantong",
    "role": "USER"
  }'
  # Response
  {
  "success": true,
  "message": "User registered successfully!",
  "userId": 4,
  "email": "cantongalma487@gmail.com"
  }
  # Login
  curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "cantongalma487@gmail.com&password=123456" \
  -c cookies.txt
  # Logout
  curl -X POST http://localhost:8081/api/v1/auth/logout -b cookies.txt