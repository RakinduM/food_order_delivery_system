README.txt

Project Title: Foodify - Full Stack Food Delivery App

Overview:
---------
Foodify is a food delivery web application built using React for the frontend and Spring Boot microservices for the backend. The app supports user registration, restaurant listings, placing orders, delivery tracking, and notification services.

Architecture:
-------------
Frontend:
- React.js (Port: 8081)

Backend Microservices (Spring Boot):
1. Restaurant Service   - Port: 8080
2. Order Service        - Port: 8090
3. User Service         - Port: 8089
4. Delivery Service     - Port: 8086
5. Notification Service - Port: 8092
6. Payment Service - Port 8091

Each service runs independently and communicates via REST APIs.

Technologies Used:
------------------
Frontend:
- React.js
- Axios
- React Router
- Tailwind CSS (or other CSS frameworks)

Backend (per service):
- Spring Boot
- Spring Web, Spring Data JPA
- MongoDB
- Eureka (optional - for service discovery)
- Maven

Getting Started:
----------------

1. Clone the Repository:
   git clone https://github.com/RakinduM/food_order_delivery_system.git

2. Backend Setup:
   For each microservice (navigate into each folder and run):

   a) Restaurant Service (Port 8080):
      cd backend/restaurant-service
      mvn spring-boot:run

   b) Order Service (Port 8090):
      cd ../order-service
      mvn spring-boot:run

   c) User Service (Port 8089):
      cd ../user-service
      mvn spring-boot:run

   d) Delivery Service (Port 8086):
      cd ../delivery-service
      mvn spring-boot:run

   e) Notification Service (Port 8092):
      cd ../notification-service
      mvn spring-boot:run

f) Payment Service (Port 8091)
cd ../notification-service
mvn 

   Make sure to configure each service’s `application.properties` for correct port and DB settings.

3. Frontend Setup:
   cd frontend
   npm install
   npm run dev

   (Ensure backend service URLs are correctly set in Axios/baseURL)

4. Access the Application:
   Frontend: http://localhost:3000/
   API Endpoints: 
     - Restaurant: http://localhost:8080/api/restaurants
     - Orders: http://localhost:8090/api/orders
     - Users: http://localhost:8089/api/users
     - Delivery: http://localhost:8086/api/delivery
     - Notifications: http://localhost:8092/api/notifications
     - Payment: http://localhost:8120/api/Payment

CORS:
-----
Enable CORS in each backend service for communication with frontend:
Example (in each controller or global config):
  @CrossOrigin(origins = "http://localhost:3000")

Environment Configuration:
--------------------------
Use `.env` in React and `application.properties` or `pom.xml` in Spring Boot services to manage ports, DB settings, and service URLs.

Production Build:
-----------------
Frontend:
  npm run build → Generates optimized static files
Backend:
  mvn clean package → Creates JAR files for each service

Hosting & Deployment:
---------------------
- Host backend services on cloud VMs or Docker containers
- Serve frontend via static server (Netlify, Vercel, or NGINX)

