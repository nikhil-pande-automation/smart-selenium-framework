# Smart Selenium Framework

# Framework Architecture

> Author : Nikhil Pande
>
> Current Version : v0.6.0
>
> Last Updated : 30-Jun-2026

---

# Purpose

This document explains the complete architecture of the Smart Selenium Framework.

Unlike FrameworkEvolution.md or GitCommitHistory.md, this document focuses on **how every class collaborates with the others during execution**.

It answers the following questions:

- How does framework execution start?
- Which class calls which class?
- Why is each layer required?
- Which design patterns are used?
- Where should future classes be added?
- How does browser management work?
- How does configuration flow through the framework?
- How does Page Object Model fit into the overall architecture?

The objective is that any developer should be able to understand the framework architecture without opening the Java source code.

---

# High Level Architecture

```
                     Feature File
                          │
                          ▼
                    Test Runner
                          │
                          ▼
                     TestNG Runner
                          │
                          ▼
                     Cucumber Hooks
                 (@Before / @After)
                          │
                          ▼
                    DriverFactory
                          │
                          ▼
                    DriverManager
                    (ThreadLocal)
                          │
                          ▼
                     WebDriver
                          │
                          ▼
                      BasePage
                          │
          ┌───────────────┴───────────────┐
          ▼                               ▼
      LoginPage                      HomePage
          │                               │
          └───────────────┬───────────────┘
                          ▼
                     WaitFactory
                          │
                          ▼
                    Selenium APIs
```

---

# Complete Execution Flow

```
Feature File

↓

Runner

↓

TestNG

↓

Hooks (@Before)

↓

DriverFactory

↓

DriverManager

↓

ChromeDriver

↓

Step Definition

↓

LoginPage

↓

BasePage

↓

WaitFactory

↓

WebDriver

↓

Application

↓

HomePage

↓

Assertion

↓

Hooks (@After)

↓

quit()

↓

DriverManager.remove()
```

---

# Layer Wise Architecture

## Layer 1

Feature Layer

Contains

- Feature Files

Responsibility

Defines business scenarios using Gherkin language.

Example

```
Given User launches the application
```

Business users should understand this layer.

No Selenium code belongs here.

---

## Layer 2

Runner Layer

Contains

TestRunner.java

Responsibilities

- Starts Cucumber
- Starts TestNG
- Reads Features
- Reads Step Definitions

---

## Layer 3

Hooks Layer

Contains

Hooks.java

Responsibilities

@Before

Create browser

@After

Close browser

Reason

Browser lifecycle should never be managed inside Step Definitions.

---

## Layer 4

Driver Layer

Contains

DriverFactory

DriverManager

BrowserType

Responsibilities

Create browser

Store browser

Return browser

Destroy browser

Design Patterns

Factory Pattern

ThreadLocal

Reason

Browser creation and browser storage should remain independent.

---

## Layer 5

Configuration Layer

Contains

ConfigReader

FrameworkConstants

Responsibilities

Read

- Browser
- URL
- Username
- Password
- Waits

Purpose

Entire framework configuration comes from one place.

Design Pattern

Singleton

---

## Layer 6

Wait Layer

Contains

WaitFactory

Responsibilities

Centralize explicit waits.

Examples

Visibility

Clickable

Title

Invisibility

Purpose

Prevent duplicated WebDriverWait code.

---

## Layer 7

Base Page Layer

Contains

BasePage

Responsibilities

Common Selenium operations.

Examples

click()

enterText()

openApplication()

getText()

isDisplayed()

Purpose

Avoid duplicate Selenium code inside Page Objects.

---

## Layer 8

Page Object Layer

Contains

LoginPage

HomePage

Responsibilities

Store page locators.

Perform page actions.

Never contain browser creation code.

Never contain wait creation code.

Only business actions.

Example

login()

logout()

transferFunds()

registerUser()

---

## Layer 9

Step Definition Layer

Responsibilities

Connect Gherkin steps with Page Objects.

Example

```
Given User launches application
```

↓

```
loginPage.login(...)
```

Never write Selenium code here.

---

# Current Folder Structure

```
src

├── main
│
│   ├── java
│   │
│   └── com.nikhil.framework
│
│       ├── config
│       ├── constants
│       ├── driver
│       ├── enums
│       ├── pages
│       └── waits
│
└── test
    │
    ├── java
    │
    │   └── com.nikhil.tests
    │
    │       ├── hooks
    │       ├── runners
    │       └── stepdefinitions
    │
    └── resources
        │
        └── features
```

---

# Current Design Patterns

The framework currently uses the following design patterns.

Singleton

Used In

ConfigReader

Reason

Single configuration object throughout framework.

------------------------------------------------

Factory Pattern

Used In

DriverFactory

Reason

Browser creation should remain isolated.

------------------------------------------------

Page Object Model

Used In

LoginPage

HomePage

Reason

Separate page behaviour from test logic.

------------------------------------------------

ThreadLocal Pattern

Used In

DriverManager

Reason

Safe browser execution during parallel testing.

---

# Current SOLID Principles

Single Responsibility Principle

Examples

DriverFactory

Creates browser only.

DriverManager

Stores browser only.

ConfigReader

Reads configuration only.

WaitFactory

Handles waits only.

BasePage

Common Selenium actions only.

Each class has exactly one reason to change.

---

# Current Framework Status

Implemented

✔ Maven

✔ Selenium

✔ TestNG

✔ Cucumber

✔ DriverFactory

✔ DriverManager

✔ BrowserType

✔ ConfigReader

✔ WaitFactory

✔ BasePage

✔ LoginPage

✔ HomePage

✔ Login Automation

✔ Assertions

Architecture Status

Stable

Ready for Utility Layer development.

---

# Future Architecture

The following layers will be added.

Utilities Layer

Contains

- JavaScript Utility
- Screenshot Utility
- Alert Utility
- Window Utility
- Frame Utility
- Dropdown Utility

Reporting Layer

Contains

Extent Reports

Logging Layer

Contains

Log4j2

Listener Layer

Contains

TestNG Listeners

Data Layer

Contains

Excel Data Provider

Parallel Layer

Contains

Thread-safe execution support.

CI/CD Layer

Contains

Docker

Jenkins

GitHub Actions

---

# Architecture Philosophy

The framework follows one simple rule.

Each class should have one responsibility.

Each layer should communicate only with its immediate neighbouring layer.

This minimizes coupling, improves readability, simplifies maintenance, and allows the framework to grow without requiring major architectural changes.