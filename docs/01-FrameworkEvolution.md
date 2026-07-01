01-FrameworkEvolution.md

# Smart Selenium Framework - Framework Evolution

> Author: Nikhil Pande
>
> Framework Name: Smart Selenium Framework
>
> Current Version: v0.5.0
>
> Last Updated: 30-Jun-2026

---

# Objective

This document records the complete engineering journey of the Smart Selenium Framework.

It is not just a change log.

It explains:

- Why a component was introduced.
- Why a particular design pattern was selected.
- How the architecture evolved.
- Which Git commit introduced the change.
- How each class is connected with the rest of the framework.
- Future improvements planned for each component.

The goal is that even after several months, any developer (including myself) should understand how and why the framework evolved.

---

# Framework Vision

The objective of this framework is to build an enterprise-level Selenium Automation Framework using modern Java and Selenium design principles.

The framework should be:

- Maintainable
- Reusable
- Scalable
- Thread Safe
- Parallel Execution Ready
- Easy to Debug
- Easy to Extend
- Interview Ready
- Production Ready

---

# Technology Stack

Language
- Java (Java 17 for compilation/runtime)
- Java 8 coding style and language features

Automation
- Selenium 4

BDD
- Cucumber

Test Runner
- TestNG

Build Tool
- Maven

Version Control
- Git
- GitHub

IDE
- IntelliJ IDEA

Operating System
- Windows
- WSL Ubuntu

---

# Framework Evolution Timeline

v0.1
↓

Initial Maven Project

↓

v0.2

Framework Package Structure

↓

v0.3

Framework Constants

↓

v0.4

DriverManager (ThreadLocal)

↓

v0.5

DriverFactory

↓

v0.6

BrowserType Enum

↓

v0.7

ConfigReader (Singleton)

↓

v0.8

Cucumber Integration

↓

v0.9

WaitFactory

↓

v0.10

BasePage

↓

v0.11

LoginPage

↓

v0.12

First Login Automation

↓

(Current)

Refactoring to Modern By Locator Strategy

---

# Version Details

------------------------------------------------------

Version : v0.1

Title

Initial Maven Project Setup

Git Commit

Initial Maven Project setup

Objective

Create the foundation of the automation framework.

Files Added

pom.xml

Design Decisions

Maven was selected because it is the industry standard dependency management tool.

Benefits

- Automatic dependency download
- Standard project structure
- Easy CI/CD integration

Future Impact

Every future component depends on Maven.

------------------------------------------------------

Version : v0.2

Title

Framework Package Structure

Git Commit

Create initial framework package structure

Objective

Separate framework code from test code.

Packages Introduced

config

constants

driver

enums

pages

tests

utilities

waits

Reason

Following Single Responsibility Principle from the beginning makes the framework easier to maintain.

Future

New packages will be added without disturbing existing architecture.

------------------------------------------------------

Version : v0.3

Title

Framework Constants

Git Commit

Add framework constants

Classes

FrameworkConstants.java

Objective

Centralize all fixed project paths.

Examples

Project Root

Reports Folder

Screenshots Folder

Logs Folder

Reason

Avoid hardcoded paths throughout the project.

Future

Additional constant paths will be added here.

------------------------------------------------------

Version : v0.4

Title

DriverManager using ThreadLocal

Git Commit

Implement DriverManager using ThreadLocal

Class

DriverManager.java

Objective

Store browser instances separately for each execution thread.

Reason

Static WebDriver breaks parallel execution because every thread shares one browser.

ThreadLocal provides

Thread-1 → Chrome1

Thread-2 → Chrome2

Thread-3 → Chrome3

Design Pattern

ThreadLocal

Benefits

Parallel Execution Ready

Interview Topics

Why ThreadLocal?

Why static?

Why final?

Why remove() instead of set(null)?

Future

Parallel execution will directly depend on DriverManager.

------------------------------------------------------

Version : v0.5

Title

DriverFactory

Git Commit

Implement DriverFactory with browser initialization

Class

DriverFactory.java

Objective

Create browsers only.

Responsibilities

Create Chrome

Create Edge

Create Firefox

Store browser inside DriverManager

Design Pattern

Factory Pattern

Reason

Object creation should not be mixed with browser storage.

Relationship

DriverFactory

↓

DriverManager

↓

WebDriver

------------------------------------------------------

Version : v0.6

Title

BrowserType Enum

Git Commit

BrowserType enum introduced, switch conditions updated DriverFactory.java, config.properties data added

Objective

Avoid String comparison.

Reason

Enums provide compile-time safety.

Old

if(browser.equals("chrome"))

New

BrowserType.CHROME

Benefits

Cleaner switch statement

No typing mistakes

Better readability

------------------------------------------------------

Version : v0.7

Title

ConfigReader

Git Commit

Implement ConfigReader using Singleton pattern

Class

ConfigReader.java

Design Pattern

Singleton

Reason

Configuration should only be loaded once.

Responsibilities

Read

browser

url

username

password

timeouts

headless

screenshots

Relationship

Config.properties

↓

ConfigReader

↓

Framework

Future

All framework configuration will come through ConfigReader.

------------------------------------------------------

Version : v0.8

Title

BDD Framework Setup

Git Commit

Refactor project structure for Cucumber BDD framework

Objective

Introduce Behavior Driven Development.

Components

Runner

Hooks

Feature

Step Definitions

Reason

Business readable automation.

Flow

Feature

↓

Runner

↓

Hooks

↓

Step Definition

------------------------------------------------------

Version : v0.9

Title

WaitFactory

Objective

Centralize explicit waits.

Reason

Avoid duplicate WebDriverWait code.

Future

Visibility

Clickable

Invisible

Title

URL

Frame

All waits will belong here.

------------------------------------------------------

Version : v0.10

Title

BasePage

Objective

Centralize common Selenium actions.

Methods

click()

enterText()

getText()

openApplication()

Reason

Page Objects should contain business logic only.

------------------------------------------------------

Version : v0.11

Title

LoginPage

Objective

Create first reusable Page Object.

Responsibilities

Store locators

Perform Login

Navigate to Home Page

Reason

Separate Selenium code from Step Definitions.

------------------------------------------------------

Version : v0.12

Title

First Login Automation

Objective

Validate complete framework execution.

Execution Flow

Runner

↓

Hooks

↓

DriverFactory

↓

DriverManager

↓

LoginPage

↓

BasePage

↓

WaitFactory

↓

WebDriver

Status

PASS

Framework is now capable of executing a complete login scenario successfully.

---

# Current Framework Status

Completed

✔ Maven

✔ Selenium

✔ TestNG

✔ Cucumber

✔ DriverManager

✔ DriverFactory

✔ BrowserType

✔ ConfigReader

✔ WaitFactory

✔ BasePage

✔ LoginPage

✔ Login Automation

In Progress

Modern By Locator Refactoring

Upcoming

HomePage

Utilities

Reports

Logging

Listeners

Excel DDT

Parallel Execution

Docker

Jenkins

v1.0 Production Ready Framework