# Online Examination System

## Overview

The `` Online Examination System `` is a full-stack application designed to conduct online exams with both multiple-choice (MCQ) and programming sections. This platform provides features for creating, managing, and taking exams while ensuring a secure and user-friendly experience. The project leverages Spring Boot, React.js, and SQL Express to deliver a robust system for administering online tests.

Key functionalities include student profile management, question management, exam setup, and real-time exam monitoring. Security measures, such as preventing tab changes and browser warnings, ensure exam integrity. The module also supports customizations like exam design by difficulty level and question categorization, enhancing both functionality and user experience.

<!-- Find more details on this project in the documentation below. -->

## Important Links

| Content           | Link                                       |
| ----------------- | ------------------------------------------ |
| API Documentation | [click here](https://documenter.getpostman.com/view/32807699/2sAY4yfgmp)                            |
| Data Model          | [click here](https://app.eraser.io/workspace/3CXNu48LUM1W0jHVxsxp?origin=share) |

## Technologies

- **Frontend:** React.js
- **Backend:** Spring Boot
- **Database:** SQL Express
- **Security:** JWT for user authentication

## Installation and Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/Yash-Zanzarukiya/Online-Examination-System.git
    ```

    ```bash
    cd Online-Examination-System
    ```

### Backend Setup

1. Requirements: JDK 17, Maven, SQL server.

2. Run mvn clean install to install all dependencies

3. Update application.properties and application.yml with the required values

4. Run (mvn spring-boot:run) to run the backend

### Fronted Setup

1. **Install dependencies:**

    ```bash
    cd client
    npm install
    ```

2. **Start the server:**

    ```bash
    npm run dev
    ```

