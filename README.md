# 🏟️ Offer Application

An example application offering clients a grasp of the sport events market.

---

## 📋 Overview

This application demonstrates a sports event platform backend with the following capabilities:

- Exposes endpoints for updating events and markets and querying events by start date
- Publishes and consumes messages on two **Kafka** topics
- Sends **notifications via WebSocket**
- **Initial data** is loaded from JSON files into an **in-memory database**
- **Expired events** are cleaned automatically every **1 minute**
- **APIs and DTOs** are **automatically generated** from an **OpenAPI spec file**

---

## 🚀 Getting Started

### Prerequisites

- Java 21
- Docker & Docker Compose
- Git
- IntelliJ IDEA (recommended)

---

### Setup Instructions

Clone the Repository
```bash
https://github.com/djordjijeradulovic-hue/offer.git
```

Update Hosts File

Add following entries to the hosts file to avoid some annoying exceptions when running on localhost

```bash
127.0.0.1   kafka
127.0.0.1   zookeeper
127.0.0.1   kafdrop
```

Open the project in IntelliJ IDEA.

Enable Annotation Processing:
Go to Preferences > Build, Execution, Deployment > Compiler > Annotation Processors
and check "Enable annotation processing"

Run clean install with maven or following command if you have maven setup locally.
This will generate DTOs and APIs by specification provided in openapi.yml
```bash
mvn clean install 
```

Run docker container
```bash
docker-compose up -d
```

Run the main application class from IntelliJ or use the terminal.
```bash
mvn spring-boot:run
```

## URLs

Application
```bash
http://localhost:8080/
```

In-Memory DB
```bash
http://localhost:8080/h2-console
jdbcUrl: jdbc:h2:mem:testdb
username: sa
password:
```

Kafka UI (kafdrop)
```bash
http://localhost:9000/
```

OpenAPI documentation
```bash
http://localhost:8080/swagger-ui/index.html
```
WebSocket
```bash
ws://localhost:8080/ws/events
```