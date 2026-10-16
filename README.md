# Platform FPL

A Spring Boot web application for managing a course platform with teacher and student roles. The application provides authentication, registration, course publication, student enrollment, and dashboards for each role.

## Project overview

This project is built with:

- Java 17
- Spring Boot 4.0.2
- Spring Security
- Spring Data JPA
- MySQL
- JSP / JSTL views
- Maven
- WAR packaging

The application exposes a web interface under `src/main/webapp/WEB-INF/jsp` and uses MySQL as its data source.

## Features

- User registration and login
- Role-based access for teachers and students
- Course catalog management
- Course publication and detail pages
- Student enrollment flow
- Teacher dashboard with student/course information
- Access control and restricted pages

## Project structure

```text
platformFPL/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── fpl/platform/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── static/
│   │   │   └── templates/
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── jsp/
│   └── test/
│       └── java/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Prerequisites

Before running the project, make sure you have:

- Java 17+
- Maven 3.9+
- MySQL installed and running
- A database named `cours_platform`

## Configuration

The project configuration is defined in `src/main/resources/application.properties`.

Default values:

```properties
spring.application.name=platform
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

spring.datasource.url=jdbc:mysql://localhost:3306/cours_platform
spring.datasource.username=root
spring.datasource.password=mysql
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

server.port=8084
```

If your MySQL credentials differ, update the datasource section accordingly.

## Run the application

From the project root:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

On Windows:

```powershell
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

Then open:

```text
http://localhost:8084/
```

## Database notes

The application uses JPA and will automatically create or update tables based on the entity model (`ddl-auto=update`). Ensure the database exists before starting the application.

## Main entry point

```java
fpl.platform.PlatformApplication
```

## Typical user flow

1. User accesses the login page.
2. User registers or logs in.
3. Role is checked to redirect to the correct dashboard.
4. Teachers can publish and manage courses.
5. Students can browse the catalog and enroll in courses.

## Notes

- The app is configured for JSP-based pages rather than a REST-only API.
- Security is enabled and access control is enforced through controllers and Spring Security configuration.
- The project includes both Java controllers and a `rest` package for API-style endpoints.

## License

This project does not currently declare a specific license in the Maven configuration.
