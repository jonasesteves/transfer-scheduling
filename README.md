# Transfer Scheduling

## Overview

This project consists of a *technical challenge* developed as part of a recruitment process for a company in the Banking sector. The application exposes REST APIs for scheduling bank transfers, applying specific fee calculation rules according to the transfer amount and the scheduled date.

The project was developed using **Java 21**, **Spring Boot**, **Gradle**, and the **IntelliJ IDEA Community Edition** IDE. For data persistence, the **H2** in-memory database is used. Tests were implemented using **JUnit 5**.

---

## Author

- **Name:** Jonas Esteves
- **Email:** jonasestevess@gmail.com

---

## Functional Requirements

The application must provide APIs that allow:

- Creating bank transfer schedules;
- Updating existing schedules;
- Deleting schedules;
- Retrieving schedules.

For each scheduled transaction, a transfer fee must be calculated and applied according to the rules below.

---

## Fee Calculation Rules

### Fee A
**Transfer amount:** between €0 and €1,000

- **Scheduled date equal to the current date:**
    - 3% of the transaction amount + €3.00

### Fee B
**Transfer amount:** between €1,001 and €2,000

- **Scheduled date between 1 and 10 days from the current date:**
    - 9% of the transaction amount

### Fee C
**Transfer amount:** greater than €2,000

- **Scheduled date between 11 and 20 days:**
    - 8.2% of the transaction amount
- **Scheduled date between 21 and 30 days:**
    - 6.9% of the transaction amount
- **Scheduled date between 31 and 40 days:**
    - 4.7% of the transaction amount
- **Scheduled date greater than 40 days:**
    - 1.7% of the transaction amount

> **Note:** Unit tests are not mandatory, but they are considered a plus.

---

## Running the Project

### Prerequisites

- Java 21 installed on the machine
- (Optional) Docker and Docker Compose

---

### Option 1: Running via JAR

Download the `.jar` file from the link below:

```
https://drive.google.com/file/d/1GTscbk-Bv56RWM-xD3Q8TCqKqmqszUYJ/view?usp=sharing
```

Then run in the terminal:

```bash
java -jar transfer-scheduling-0.0.1-SNAPSHOT.jar
```

---

### Option 2: Running with Docker

With Docker installed, after cloning the repository, run the command below from the project root:

```bash
docker compose up -d
```

---

### Option 3: Running via Gradle

After cloning the repository, open the project in **IntelliJ IDEA Community Edition** or use the terminal and run:

```bash
./gradlew clean build
./gradlew clean bootJar
java -jar build/libs/transfer-scheduling-0.0.1-SNAPSHOT.jar
```

---

## API Documentation

After the application starts, the API documentation will be available at:

```
http://localhost:8080/swagger-ui/index.html
```

The Swagger interface allows you to view the available endpoints as well as execute requests directly from the web interface.

Additionally, this repository provides the **`Transfer Scheduling.postman_collection.json`** file, containing all requests preconfigured for execution via **Postman**.
