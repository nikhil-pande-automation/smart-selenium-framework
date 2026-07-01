# Smart Selenium Framework

# Design Patterns

> Author : Nikhil Pande
>
> Current Framework Version : v0.6.0
>
> Last Updated : 30-Jun-2026

---

# Purpose

This document explains every design pattern used in the Smart Selenium Framework.

The purpose is not only to understand what a design pattern is, but also:

- Why it was selected.
- Where it is used.
- Why other approaches were rejected.
- How it improves the framework.
- Common interview questions.
- Future enhancements related to the pattern.

This document should be read together with:

- 01-FrameworkEvolution.md
- 02-GitCommitHistory.md
- 03-Architecture.md

---

# What is a Design Pattern?

A Design Pattern is a proven software design solution to a commonly occurring problem.

Design patterns do NOT provide ready-made code.

Instead, they provide a reusable design approach that improves software quality.

The Smart Selenium Framework currently uses the following patterns:

- Singleton Pattern
- Factory Pattern
- Page Object Model (POM)
- ThreadLocal Pattern
- Layered Architecture

More patterns will be introduced in future versions.

---

# Pattern 01 : Singleton Pattern

Used In

ConfigReader.java

Purpose

Only one configuration object should exist during the entire execution of the framework.

---

## Problem

Suppose ConfigReader is created multiple times.

Example

```
new ConfigReader();

new ConfigReader();

new ConfigReader();
```

Every object would:

- Open config.properties
- Read the file
- Allocate memory
- Perform the same work repeatedly

This wastes memory and execution time.

---

## Solution

Create only one ConfigReader object.

Every class shares the same object.

```
Framework

        │

        ▼

ConfigReader (Single Object)

        │

        ├──────── DriverFactory

        ├──────── BasePage

        ├──────── WaitFactory

        └──────── Hooks
```

---

## Implementation

```
private static ConfigReader instance;
```

```
public static ConfigReader getInstance()
```

---

## Why Private Constructor?

```
private ConfigReader()
```

Prevents

```
new ConfigReader();
```

from outside the class.

Only getInstance() can create the object.

---

## Advantages

✔ Memory efficient

✔ Configuration loaded once

✔ Easy maintenance

✔ Better performance

---

## Disadvantages

Improper implementation can cause thread safety issues.

Current framework executes configuration loading only once before tests start, therefore this implementation is sufficient.

---

## Future Impact

Every future configuration

- Reports
- Browser
- Timeouts
- Environment
- Execution Mode

will come through ConfigReader.

---

## Interview Questions

Q.

Why Singleton?

A.

Configuration should only be loaded once and reused throughout the framework.

---

Q.

Why constructor is private?

A.

To prevent external object creation.

---

Q.

How many ConfigReader objects exist?

A.

Exactly one.

---

# Pattern 02 : Factory Pattern

Used In

DriverFactory.java

Purpose

Separate browser creation from browser usage.

---

## Problem

Without Factory Pattern

Every test would write

```
new ChromeDriver();
```

or

```
new FirefoxDriver();
```

This duplicates code.

Browser creation becomes scattered throughout the project.

---

## Solution

Only DriverFactory creates browsers.

```
DriverFactory

↓

ChromeDriver

↓

EdgeDriver

↓

FirefoxDriver
```

---

## Responsibilities

Create browser

Configure browser

Store browser

Return browser

---

## Why DriverFactory?

Because object creation belongs in one place.

Storage belongs somewhere else.

That is why

DriverFactory

creates browser

while

DriverManager

stores browser.

This follows the Single Responsibility Principle.

---

## Advantages

✔ Cleaner code

✔ Easy browser addition

✔ Reusable

✔ Centralized browser configuration

---

## Future Impact

Adding Safari later will only require DriverFactory modification.

---

## Interview Questions

Q.

Why not create ChromeDriver inside Step Definitions?

A.

Because browser creation should remain centralized.

---

Q.

Which design pattern is DriverFactory using?

A.

Factory Pattern.

---

# Pattern 03 : ThreadLocal Pattern

Used In

DriverManager.java

Purpose

Provide one browser instance per executing thread.

---

## Problem

Using

```
static WebDriver driver;
```

during parallel execution.

Thread 1

↓

Chrome

Thread 2

↓

Firefox

Thread 2 overwrites Thread 1.

Parallel execution fails.

---

## Solution

ThreadLocal

```
Thread 1

↓

Chrome

---------------------

Thread 2

↓

Edge

---------------------

Thread 3

↓

Firefox
```

Each thread receives its own WebDriver.

---

## Implementation

```
private static final ThreadLocal<WebDriver> DRIVER
```

---

## Why static?

Only one ThreadLocal container is required.

---

## Why final?

Reference should never change.

Only its contents change.

---

## Why remove()?

```
DRIVER.remove();
```

removes the complete entry.

Better than

```
set(null)
```

because it avoids stale thread references.

---

## Advantages

✔ Parallel execution ready

✔ Thread safe

✔ Independent browsers

✔ Cleaner framework

---

## Interview Questions

Why ThreadLocal?

Why static?

Why final?

Difference between remove() and set(null)?

---

# Pattern 04 : Page Object Model (POM)

Used In

LoginPage

HomePage

Future Page Objects

Purpose

Separate Selenium implementation from business logic.

---

## Problem

Without POM

Step Definition contains

```
findElement()

click()

sendKeys()
```

Business flow and Selenium implementation become mixed together.

Maintenance becomes difficult.

---

## Solution

Move Selenium operations into dedicated Page classes.

```
Step Definition

↓

LoginPage

↓

BasePage

↓

WaitFactory

↓

WebDriver
```

---

## Responsibilities

Page Object

Stores

- Locators
- Page Actions

Step Definition

Stores

Business Flow

---

## Example

Instead of

```
driver.findElement(...).click();
```

write

```
loginPage.login();
```

Cleaner.

Reusable.

Maintainable.

---

## Advantages

✔ Reusable

✔ Readable

✔ Easy maintenance

✔ Low duplication

---

## Future Impact

Every application screen becomes one Page Object.

Examples

LoginPage

HomePage

TransferFundPage

RegistrationPage

AdminPage

CustomerPage

---

## Interview Questions

Why Page Object Model?

Difference between POM and PageFactory?

Should assertions exist inside Page Objects?

(Current framework keeps assertions outside page objects whenever possible.)

---

# Pattern 05 : Layered Architecture

Purpose

Separate responsibilities into logical layers.

Current Layers

```
Feature

↓

Runner

↓

Hooks

↓

Page Objects

↓

BasePage

↓

WaitFactory

↓

DriverManager

↓

WebDriver
```

Every layer communicates only with the layer immediately below it.

This reduces coupling and improves maintainability.

---

# Design Principles Followed

Single Responsibility Principle (SRP)

Examples

DriverFactory

Only creates browsers.

DriverManager

Only stores browsers.

ConfigReader

Only reads configuration.

WaitFactory

Only manages waits.

BasePage

Only contains reusable Selenium actions.

LoginPage

Only contains Login page actions.

HomePage

Only contains Home page actions.

---

# Patterns Planned for Future Versions

The framework will later introduce additional patterns.

Builder Pattern

Used for test data creation.

Strategy Pattern

Used for browser execution strategies.

Facade Pattern

Used for utility aggregation.

Decorator Pattern

Potentially used for reporting enhancements.

These will be added only when a real engineering problem justifies their introduction.

---

# Summary

Current Design Patterns

✔ Singleton

✔ Factory

✔ ThreadLocal

✔ Page Object Model

✔ Layered Architecture

Current SOLID Principle

✔ Single Responsibility Principle

These patterns form the architectural foundation of the Smart Selenium Framework.

Future versions will continue to extend the framework while preserving these core design principles.