# 🚀 Smart Selenium Framework

> An enterprise-ready Selenium automation framework built from scratch
> using **Java, Selenium 4, Cucumber BDD, TestNG, Maven, Docker,
> Selenium Grid and Jenkins**.

------------------------------------------------------------------------

## 📖 Overview

Smart Selenium Framework was designed to simulate how a real-world
automation framework evolves inside an organization. The focus is not
only on UI automation but also on software engineering practices such as
clean architecture, thread safety, scalability, maintainability, CI
integration, and reusable framework components.

## ✨ Highlights

-   Selenium 4
-   Java 17 (Java 8 coding style where practical)
-   Cucumber BDD + TestNG
-   Page Object Model
-   ThreadLocal WebDriver
-   ThreadLocal ExtentTest
-   Explicit Wait framework
-   BrowserManager / EnvironmentManager / ExecutionManager
-   Local & Remote execution
-   Docker + Selenium Grid
-   Jenkins parameterized builds
-   Parallel execution
-   Extent Reports with screenshots
-   Log4j2 logging
-   Excel, JSON and Properties based test data

------------------------------------------------------------------------

# 🏗 Framework Architecture

``` text
Feature
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
   ├── LOCAL
   │      └── ChromeDriver / FirefoxDriver / EdgeDriver
   │
   └── REMOTE
          └── RemoteWebDriver
                 │
                 ▼
          Selenium Grid
                 │
                 ▼
          Browser Container
                 │
                 ▼
            Application
```

------------------------------------------------------------------------

# 🔄 Parallel Execution

``` text
Scenario 1 ─┐
Scenario 2 ─┼── TestNG DataProvider(parallel=true)
Scenario 3 ─┘
         │
         ▼
 ThreadLocal<WebDriver>
         │
         ▼
 Independent browser session per thread
```

------------------------------------------------------------------------

# 🧰 Technology Stack

  Technology         Details
  ------------------ -------------------------
  Language           Java 17
  Selenium           Selenium 4
  BDD                Cucumber
  Test Runner        TestNG
  Build Tool         Maven
  Reporting          Extent Reports
  Logging            Log4j2
  Data               Excel, JSON, Properties
  CI                 Jenkins
  Containers         Docker
  Remote Execution   Selenium Grid

------------------------------------------------------------------------

# 📂 Key Framework Components

-   DriverFactory
-   DriverManager
-   BrowserOptionsFactory
-   BrowserManager
-   ExecutionManager
-   EnvironmentManager
-   UrlManager
-   ConfigReader (Thread-safe Holder Singleton)
-   BasePage
-   WaitFactory
-   ElementActions
-   ReportManager
-   ScreenshotUtil
-   Hooks

------------------------------------------------------------------------

# ✅ Implemented Features

## Core

-   Factory Pattern
-   Singleton Pattern
-   Page Object Model
-   Thread-safe Driver Management
-   Configuration Management
-   Environment Switching

## Automation

-   Cross Browser Execution
-   Headless Execution
-   Parallel Execution
-   Local & Remote Execution
-   Screenshot on Failure
-   Explicit Wait Framework

## Reporting

-   Extent Reports
-   Log4j2 Logging

## Test Data

-   Excel
-   JSON
-   Properties

## CI/CD

-   Jenkins Parameterized Jobs
-   Docker
-   Selenium Grid
-   Parallel Remote Execution

------------------------------------------------------------------------

# ▶ Execution

## Local

``` bash
mvn clean test
```

## Chrome

``` bash
mvn clean test -Dbrowser=chrome
```

## Headless

``` bash
mvn clean test -Dheadless=true
```

## Remote Grid

``` bash
mvn clean test -Dexecution=remote -Dgrid.url=http://localhost:4444/wd/hub
```

------------------------------------------------------------------------

# 🧪 Jenkins Parameters

-   BROWSER
-   HEADLESS
-   EXECUTION
-   ENVIRONMENT
-   THREADS

------------------------------------------------------------------------

# 💡 Design Patterns

-   Factory
-   Singleton (Holder Idiom)
-   Page Object Model

# SOLID Principles

-   Single Responsibility Principle
-   Separation of Concerns
-   Dependency separation between creation and storage
-   Reusable utility classes

------------------------------------------------------------------------

# 📈 Project Status

**Version:** v1.0

Completed:

-   Framework Core
-   Parallel Execution
-   Docker Execution
-   Selenium Grid
-   Jenkins Integration
-   Data Driven Testing
-   Extent Reporting
-   Logging
-   Cross Browser Support

------------------------------------------------------------------------

# 👨‍💻 Author

**Nikhil Pande**

Software QA Automation Engineer

This framework was built incrementally with production-oriented
engineering practices and serves as both a learning project and an
interview-ready automation framework.

-------------------------------------------------------------------------

Current status

framework now includes:

✅ Selenium 4
✅ Java 17 (Java 8 coding style)
✅ Cucumber + TestNG
✅ Page Object Model
✅ ThreadLocal WebDriver
✅ Thread-safe ConfigReader
✅ BrowserManager
✅ EnvironmentManager
✅ ExecutionManager
✅ GridManager
✅ BrowserOptionsFactory
✅ WaitFactory
✅ ElementActions
✅ Excel + JSON test data
✅ Extent Reports
✅ Log4j2
✅ Screenshot on failure
✅ Local execution
✅ Remote execution
✅ Docker
✅ Selenium Grid
✅ Jenkins
✅ Jenkins Parameterized Builds
✅ Parallel execution
✅ Professional README