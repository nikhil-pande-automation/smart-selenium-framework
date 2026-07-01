# 🚀 Smart Selenium Framework

> A scalable, reusable, and enterprise-ready Selenium Automation Framework built using Java, Selenium 4, Cucumber BDD, TestNG, and Maven.

---

## 📖 About the Project

Smart Selenium Framework is an industry-oriented automation framework developed from scratch with a strong focus on software engineering principles rather than simply automating test cases.

The objective of this project is to simulate how a real-world automation framework evolves inside a software organization.

Instead of concentrating only on Selenium scripts, this project demonstrates:

- Framework Architecture
- Design Patterns
- SOLID Principles
- Clean Code Practices
- Reusability
- Maintainability
- Scalability
- Thread Safety
- Enterprise Framework Design

This repository also contains detailed documentation explaining every engineering decision made during the development of the framework.

---

# 🎯 Project Goals

- Build an enterprise-level Selenium framework from scratch.
- Follow modern automation framework architecture.
- Apply software engineering best practices.
- Keep the framework reusable and maintainable.
- Support future parallel execution.
- Support Data Driven Testing.
- Support Cross Browser Testing.
- Integrate Reporting and Logging.
- Support CI/CD execution.

---

# ⚙️ Technology Stack

| Technology | Version |
|------------|----------|
| Java | 17 (Runtime) |
| Java Coding Style | Java 8 Compatible |
| Selenium | 4.x |
| Test Runner | TestNG |
| BDD | Cucumber |
| Build Tool | Maven |
| IDE | IntelliJ IDEA |
| Version Control | Git |
| Repository | GitHub |

---

# 🏗 Current Framework Architecture

```
Feature File
      │
      ▼
Runner
      │
      ▼
Hooks
      │
      ▼
DriverFactory
      │
      ▼
DriverManager (ThreadLocal)
      │
      ▼
WebDriver
      │
      ▼
BasePage
      │
      ▼
Page Objects
      │
      ▼
WaitFactory
      │
      ▼
Application
```

---

# 📂 Project Structure

```
smart-selenium-framework

│
├── docs
│   ├── 01-FrameworkEvolution.md
│   ├── 02-GitCommitHistory.md
│   ├── 03-Architecture.md
│   ├── 04-DesignPatterns.md
│
├── src
│   ├── main
│   │
│   └── java
│       └── com.nikhil.framework
│
│           ├── config
│           ├── constants
│           ├── driver
│           ├── enums
│           ├── pages
│           └── waits
│
│
└── test
    ├── hooks
    ├── runners
    ├── stepdefinitions
    └── features
```

---

# ✨ Features Implemented

### Framework Core

- Maven Project Structure
- Framework Constants
- Browser Configuration
- DriverFactory
- DriverManager
- ConfigReader
- BrowserType Enum

---

### Selenium

- Selenium 4
- Explicit Waits
- BasePage
- Login Automation

---

### BDD

- Cucumber
- Feature Files
- Step Definitions
- Hooks
- Runner

---

### Design

- Thread Safe Driver Management
- Singleton Pattern
- Factory Pattern
- Page Object Model
- Layered Architecture

---

# 📚 Documentation

The framework documentation is maintained alongside the source code.

| File | Description |
|------|-------------|
| 01-FrameworkEvolution.md | Framework evolution from v0.1 onward |
| 02-GitCommitHistory.md | Engineering purpose of every Git commit |
| 03-Architecture.md | Complete framework architecture |
| 04-DesignPatterns.md | Design patterns used throughout the framework |

---

# ▶️ How to Execute

Clone the repository

```
git clone https://github.com/nikhil-pande-automation/smart-selenium-framework.git
```

Move into the project

```
cd smart-selenium-framework
```

Run using Maven

```
mvn clean test
```

---

# 🧠 Design Principles

The framework follows the following principles:

- Single Responsibility Principle (SRP)
- Separation of Concerns
- Layered Architecture
- Thread Safety
- Reusability
- Maintainability
- Scalability
- Clean Code

---

# 📌 Current Status

Current Version

**v0.6.0**

Status

🟢 Active Development

Completed

- Framework Core
- Login Automation
- Configuration Layer
- Browser Management
- BDD Integration

Currently Working On

- Utility Layer
- Reporting
- Logging

---

# 🗺 Roadmap

Upcoming Modules

- JavaScript Utility
- Screenshot Utility
- Dropdown Utility
- Alert Utility
- Frame Utility
- Window Utility
- Extent Reports
- Log4j2
- TestNG Listeners
- Excel Data Driven Framework
- Parallel Execution
- Docker Execution
- Jenkins Integration

---

# 🤝 Contribution

This project is currently maintained by the author for learning, framework engineering, and interview preparation.

Future improvements will continue to follow the same architectural principles documented in the repository.

---

# 👨‍💻 Author

**Nikhil Pande**

Senior QA Automation Engineer

---

# ⭐ Repository Vision

This project is not intended to be a collection of Selenium scripts.

Its goal is to demonstrate how an automation framework evolves using engineering principles, design patterns, clean architecture, and reusable components similar to those used in enterprise software development.