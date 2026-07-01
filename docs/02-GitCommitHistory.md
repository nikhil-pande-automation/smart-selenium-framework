# Smart Selenium Framework

# Git Commit History

> Author : Nikhil Pande
>
> Current Framework Version : v0.6.0
>
> Last Updated : 30-Jun-2026

---

# Purpose

This document explains the engineering purpose behind every Git commit made during the development of the Smart Selenium Framework.

GitHub already stores the commit history.

This document answers a much more important question:

**"Why was this commit created?"**

Each milestone explains

- Objective
- Engineering Decision
- Classes Added
- Design Pattern (if any)
- Relationship with existing framework
- Future dependency
- Interview topics

This makes the repository easy to understand even after several months.

---

# Development Timeline

```
v0.1
↓

Initial Maven Project

↓

Framework Structure

↓

Framework Constants

↓

DriverManager

↓

DriverFactory

↓

BrowserType Enum

↓

ConfigReader

↓

BDD Setup

↓

Login Automation

↓

Modern By Locator Refactoring

↓

Current Version
```

---

# Commit 01

Commit Message

Initial Maven Project setup

Date

26-Jun-2026

Objective

Create the initial Maven based automation project.

Files Introduced

- pom.xml

Engineering Decision

Maven was selected because it is the industry standard dependency management tool.

Why?

Instead of manually downloading Selenium jars, Maven manages dependencies automatically.

Benefits

- Standard project structure
- Dependency management
- CI/CD friendly
- Easy version upgrades

Interview Topics

- Why Maven?
- Difference between Maven and Gradle
- Maven Lifecycle

Framework Impact

Without Maven the framework cannot exist.

------------------------------------------------------------

# Commit 02

Commit Message

create initial framework package structure

Date

26-Jun-2026

Objective

Create the initial folder hierarchy.

Packages Introduced

config

constants

driver

enums

pages

tests

utilities

waits

Engineering Decision

Project architecture should be defined before writing Selenium code.

Reason

Separating responsibilities from Day 1 avoids future refactoring.

Design Principle

Single Responsibility Principle

Future Dependency

Every future class belongs to one of these packages.

------------------------------------------------------------

# Commit 03

Commit Message

Create initial framework package structure

Date

26-Jun-2026

Objective

Finalize package organization.

Reason

Minor cleanup after the first package creation.

Impact

Created the final package structure that the framework still follows.

------------------------------------------------------------

# Commit 04

Commit Message

Add framework constants

Date

27-Jun-2026

Classes Added

FrameworkConstants.java

Objective

Centralize all framework paths.

Responsibilities

Store

- Project Root
- Report Folder
- Screenshot Folder
- Log Folder
- Config File Path

Engineering Decision

Never hardcode project paths.

Benefits

Single location for updates.

Interview Topics

Why Utility Class?

Why final?

Why private constructor?

Future Dependency

ConfigReader depends on FrameworkConstants.

------------------------------------------------------------

# Commit 05

Commit Message

Implement DriverManager using ThreadLocal

Date

27-Jun-2026

Classes Added

DriverManager.java

Objective

Store one WebDriver instance per thread.

Engineering Problem

Static WebDriver causes browser sharing during parallel execution.

Solution

ThreadLocal<WebDriver>

Design Decision

Thread-safe browser management.

Benefits

- Safe Parallel Execution
- Independent browsers
- No browser overwrite

Interview Topics

- Why ThreadLocal?
- Why static?
- Why final?
- Why remove() instead of set(null)?

Future Dependency

Parallel execution depends completely on DriverManager.

------------------------------------------------------------

# Commit 06

Commit Message

Implement DriverFactory with browser initialization

Date

27-Jun-2026

Classes Added

DriverFactory.java

Objective

Create browser objects.

Responsibilities

- Chrome
- Firefox
- Edge

Engineering Decision

Separate browser creation from browser storage.

Design Pattern

Factory Pattern

Relationship

DriverFactory

↓

DriverManager

↓

WebDriver

Interview Topics

Factory Pattern

Future Dependency

Hooks use DriverFactory.

------------------------------------------------------------

# Commit 07

Commit Message

BrowserType enum introduced, switch conditions updated DriverFactory.java, config.properties data added

Date

27-Jun-2026

Classes Added

BrowserType.java

Config Updates

config.properties

Objective

Remove String based browser comparison.

Old

```
if(browser.equals("chrome"))
```

New

```
BrowserType.CHROME
```

Engineering Decision

Enums provide compile-time safety.

Benefits

- Cleaner code
- No spelling mistakes
- Better switch statement

Interview Topics

Why Enum?

Future Dependency

DriverFactory now accepts BrowserType.

------------------------------------------------------------

# Commit 08

Commit Message

Implement ConfigReader using Singleton pattern

Date

29-Jun-2026

Classes Added

ConfigReader.java

Objective

Read framework configuration.

Design Pattern

Singleton

Engineering Decision

Configuration should only be loaded once.

Responsibilities

Read

- browser
- url
- username
- password
- waits
- headless
- screenshots

Benefits

Single object throughout framework.

Interview Topics

Singleton Pattern

Future Dependency

Entire framework configuration flows through ConfigReader.

------------------------------------------------------------

# Commit 09

Commit Message

Refactor project structure for Cucumber BDD framework

Date

29-Jun-2026

Classes Added

Runner

Hooks

Feature Files

Step Definitions

Objective

Introduce Behavior Driven Development.

Reason

Business readable automation.

Framework Flow

Feature

↓

Runner

↓

Hooks

↓

Step Definition

↓

Framework

Interview Topics

BDD

Cucumber

Hooks

Runner

Future Dependency

Every automation scenario will execute through this flow.

------------------------------------------------------------

# Commit 10

Commit Message

Implement Page Object Model with login automation

Date

30-Jun-2026

Classes Added

BasePage

WaitFactory

LoginPage

Objective

Introduce reusable Page Object Model.

Engineering Decision

Separate Selenium actions from business logic.

Architecture

Step Definition

↓

LoginPage

↓

BasePage

↓

WaitFactory

↓

DriverManager

↓

WebDriver

Benefits

- Reusability
- Maintainability
- Cleaner Step Definitions

Interview Topics

Page Object Model

BasePage

Explicit Waits

Future Dependency

All future pages extend BasePage.

------------------------------------------------------------

# Commit 11

Commit Message

First successful Login Automation after locators refactoring

Date

30-Jun-2026

Major Refactoring

PageFactory

↓

By Locator Strategy

Classes Updated

WaitFactory

BasePage

LoginPage

HomePage

Objective

Modernize the framework architecture.

Engineering Decision

Although PageFactory is still available, modern Selenium frameworks prefer By locators.

Reasons

- Better readability
- Better debugging
- Dynamic locator support
- Cleaner reusable methods

Additional Improvement

Page Transition Pattern

Old

```
login();

void
```

New

```
HomePage homePage =
login(...);
```

Benefits

Object oriented navigation.

Architecture

LoginPage

↓

HomePage

Framework Status

Framework successfully executes

- Browser Launch
- URL Navigation
- Login
- Home Page Verification
- Browser Close

Interview Topics

Modern Page Object Model

By Locator Strategy

Page Transition Pattern

Framework Layering

------------------------------------------------------------

# Current Framework Statistics

Current Version

v0.6.0

Approximate Classes

- FrameworkConstants
- DriverManager
- DriverFactory
- BrowserType
- ConfigReader
- WaitFactory
- BasePage
- LoginPage
- HomePage
- Hooks
- Runner
- Step Definitions

Design Patterns Used

✔ Singleton

✔ Factory

✔ Page Object Model

✔ ThreadLocal

✔ Layered Architecture

SOLID Principles Applied

✔ Single Responsibility Principle

Framework Status

✔ Browser Management

✔ Configuration Management

✔ BDD Setup

✔ Login Automation

✔ Modern By Locator Strategy

✔ Home Page Verification

Current Stability

Framework is stable and serves as the baseline for future enhancements.

---

# Upcoming Commits

The following milestones are planned.

- Utility Classes
- JavaScript Executor Utility
- Dropdown Utility
- Alert Utility
- Window Utility
- Frame Utility
- Screenshot Utility
- Log4j2 Integration
- Extent Reports
- Listeners
- Excel Data Driven Framework
- Parallel Execution
- Docker Execution
- Jenkins Integration
- Framework Version v1.0