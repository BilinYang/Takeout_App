# Takeout App

- **About:**  
  A software product customized specifically for restaurant and catering businesses.

---

### Admin Side

- **Employee Management:**  
  Administrators can manage employee information in the backend system, including query, add, edit, and disable functions.

- **Category Management:**  
  Manages and maintains dish categories or set-meal categories currently offered by the restaurant, including query, add, update, and delete functions.

- **Dish Management:**  
  Maintains dish information under each category, including query, add, update, delete, enable (put on sale), and disable (take off sale).

- **Set Meal Management:**  
  Maintains the restaurant’s set-meal information, including query, add, update, delete, enable, and disable functions.

- **Order Management:**  
  Manages orders placed by users via the mobile client, including query, cancel, dispatch, complete, and order report downloads.

- **Workbench:**  
  Displays various operation interfaces.

- **Data Statistics:**  
  Generates statistical data for the restaurant, such as revenue, user count, and orders.

---

### User Side

- **WeChat Login:**  
  WeChat users on the client side generate JWT tokens for authentication.

- **Product Browsing:**  
  Displays dish categories and set-meal categories in the ordering interface. Loads dishes based on the selected category for users to browse and choose.

- **Shopping Cart:**  
  Selected dishes are added to the user’s shopping cart. Features include query, add, delete, and clear shopping cart.

- **WeChat Payment:**  
  After selecting dishes or set meals, users can check out and pay for items in the shopping cart, which triggers order payment.

- **Order History:**  
  Users can view their past orders in the order history.

- **Address Management:**  
  The personal center displays the user’s basic information. Users can manage delivery addresses and view historical orders.

- **Order Reminder:**  
  After payment, if the merchant delays in accepting the order, the user can click the reminder option to prompt the merchant.

---

## Technology Stack

- **User Layer:**  
  Node.js, Vue.js, ElementUI, WeChat Mini Program, Apache ECharts  

- **Gateway Layer:**  
  Nginx  

- **Application Layer:**  
  POI, HttpClient, Spring Boot, Spring MVC, JWT, Spring Task, Spring Cache, OSS, Swagger, WebSocket  

- **Data Layer:**  
  MySQL, Redis, MyBatis, PageHelper, Spring Data Redis  

- **Tools:**  
  Postman, Git, Maven, JUnit  

---

## Environment Setup

### Frontend Environment

- Runs on **Nginx**.  
- Access port: **80**.  

### Backend Environment

- **Maven**.  
- Supports modular development.
