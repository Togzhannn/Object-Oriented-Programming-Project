# Research-Oriented University System

A console-based university management system built in Java using Object-Oriented Programming principles.

---

## Features

- User authentication
- Course registration (max 21 credits)
- Grade management
- Transcript generation
- Research module (papers, projects, h-index)
- Internal messaging system
- Reports and analytics
- Data persistence (serialization)

---

## User Roles

| Role | Main Actions |
|------|---------------|
| Student | Register for courses, view marks, rate teachers, assign supervisor (4th year) |
| Teacher | Manage courses, enter grades, participate in research |
| Manager | Approve registrations, assign teachers, publish news, generate reports |
| Admin | Manage users, block/unblock accounts, view logs, search users |

---

## Business Rules

- Students cannot exceed 21 credits
- Students cannot fail more than 3 courses
- 4th-year students need a research supervisor
- Supervisor must have h-index ≥ 3
- One course may have multiple instructors

---

## Custom Exceptions

- `CreditLimitExceededException`
- `LowHIndexException`
- `NonResearcherException`
- `InfoNotCorrectException`

---

## Design Patterns Used

| Pattern | Where |
|---------|-------|
| Singleton | Database class |
| Observer | News system (Manager → Teachers) |
| Strategy | Sorting research papers |
| Factory | User creation |

---

## Collections Used

- `ArrayList`
- `HashMap`
- `HashSet`
- `TreeSet`

---

## How to Run

1. Open project in IntelliJ IDEA
2. Run `Main.java`
3. Console demo runs automatically

---

## Technologies

- Java 17+
- OOP (inheritance, polymorphism, abstraction, interfaces)
- Java Collections Framework
- Java Serialization
- UML (StarUML)

---

## Demo Scenarios (Main.java)

- User creation & login
- Course registration with credit validation
- News publishing (Observer pattern)
- Marks and transcripts
- Fail limit validation
- Research papers & projects
- Supervisor assignment
- Messaging system
- Admin actions
- Save/load data

---

## Goals

Demonstrate practical application of OOP with:

- Low coupling
- High cohesion
- Scalability
- Clean architecture
