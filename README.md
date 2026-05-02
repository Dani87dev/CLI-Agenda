# CLI-Agenda
CLI-Agenda is a console application written in Java that allows you to manage tasks, notes, and events directly from the terminal.

---

## Features

### Tasks
- Create, list, search, update, and delete tasks
- Filter tasks by status: all, incomplete, completed
- Each task can be marked as completed/incomplete
- Tasks can be associated with an event

### Notes
- Create, list, search, update, and delete notes
- Notes must be associated with an existing task
- When creating a note, the list of available tasks is displayed

### Events
- Create, list, search, update, and delete events
- Date format validation with retry on error
- Events can be recurring
- When searching for an event, associated tasks are displayed

---

## Prerequisites
- Java 17 or higher
- Docker and Docker Compose
- Maven 3.x

---

## Usage Instructions

### 1. Build the project
```bash
mvn clean package
```
This will generate the file `target/CLI-agenda-1.0-jar-with-dependencies.jar`.

### 2. Start the MySQL database
```bash
docker-compose up -d mysql
```
The MySQL container will start on port 3306 with data persistence.

### 3. Run the application
```bash
java -jar target/CLI-agenda-1.0-jar-with-dependencies.jar
```

### 4. Stop the database (optional)
```bash
docker-compose down
```

---

## Application Menu

### Main Menu
- 1 - TASKS
- 2 - NOTES
- 3 - EVENTS
- 0 - Exit

### Tasks Submenu
- 1 - Create task
- 2 - List all tasks
- 3 - List incomplete tasks
- 4 - List completed tasks
- 5 - Find task
- 6 - Update task
- 7 - Delete task
- 0 - Back

### Notes Submenu
- 1 - Create note
- 2 - List notes
- 3 - Find note
- 4 - Update note
- 5 - Delete note
- 0 - Back

### Events Submenu
- 1 - Create event
- 2 - List events
- 3 - Find event
- 4 - Update event
- 5 - Delete event
- 0 - Back

---

## Technologies Used
- Java — main language
- JDBC — database connection
- Docker — database deployment
- Maven — dependency management
- JUnit 5 — unit testing
- MySQL 8.0 — database

---

## Architecture
- Design patterns: Singleton (ConnectionManager), Repository (interfaces + SQL implementation)
- SOLID principles applied: Single Responsibility, Dependency Inversion
- Separate structure by functionality (events, tasks, notes)
- Exception propagation from model/repository up to CLI for error traceability

---

## Project Structure
```
src/
├── main/java/com/itacademy/cliagenda/
│   ├── application/       # Entry point and menu
│   ├── event/             # Event management (model, service, repository, cli)
│   ├── task/              # Task management (model, service, repository, cli)
│   ├── note/              # Note management (model, service, repository, cli)
│   ├── infrastructure/    # Database connection (ConnectionManager)
│   └── common/            # Shared utilities
└── test/                  # Unit tests
```
---

## COPYRIGHT and LICENSE
Copyright 2025 Ulises Lafuente, Daniel Vila

CLI-Agenda is free software; you can redistribute it and/or modify it under the terms of the Apache License 2.0.

CLI-Agenda is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Apache License 2.0 for more details.
