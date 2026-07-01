# Smart Selenium Framework

# Framework Interview Guide

> Author : Nikhil Pande
>
> Current Framework Version : v0.6.0
>
> Last Updated : 30-Jun-2026

---

# Purpose

This document is the complete interview preparation guide for the Smart Selenium Framework.

Unlike ordinary interview notes, every answer in this document is directly connected to the implementation of this framework.

The objective is to ensure that every design decision made in the framework can also be confidently explained during interviews.

By the end of the framework development, this document will serve as a complete revision guide for:

- Java
- Selenium
- Automation Framework Design
- Design Patterns
- Cucumber BDD
- TestNG
- Git
- Maven
- Parallel Execution
- Reporting
- CI/CD Integration

---

# How to Use This Guide

This guide should not be memorized.

Instead:

1. Read the related source code.
2. Read the architecture documentation.
3. Read the design pattern documentation.
4. Finally, read the interview explanation here.

Understanding is always preferred over memorization.

---

# Interview Philosophy

During an interview, answering **what** is usually not enough.

A strong answer explains:

- What
- Why
- When
- Advantages
- Disadvantages
- Real project example

Example

Weak Answer

"Singleton creates one object."

Strong Answer

"In our framework ConfigReader uses the Singleton pattern because framework configuration should only be loaded once. Creating multiple ConfigReader objects would repeatedly load the same configuration file and waste resources."

Always connect answers to your own framework.

---

# Chapter 1

# Framework Fundamentals

---

## Q1.

What is an Automation Framework?

Answer

An automation framework is a structured collection of reusable components, standards, utilities, and best practices used to automate software testing efficiently.

Instead of writing Selenium scripts independently, a framework organizes the project into reusable layers such as:

- Driver Management
- Configuration
- Wait Management
- Page Objects
- Reporting
- Logging
- Test Data
- Utilities

The objective is to reduce code duplication, improve maintainability, and simplify future enhancements.

Our Smart Selenium Framework follows exactly this approach.

---

## Q2.

Why build a framework instead of writing Selenium scripts?

Answer

Direct Selenium scripts work for small projects.

As applications grow, repeated code appears everywhere.

Examples include:

- Browser initialization
- Wait logic
- Configuration reading
- Logging
- Screenshot handling

A framework centralizes these responsibilities into reusable components.

Benefits

- Reusability
- Scalability
- Easier debugging
- Lower maintenance cost
- Cleaner code
- Better team collaboration

---

## Q3.

Explain your framework architecture.

Answer

Our framework follows a layered architecture.

Execution Flow

Feature File

↓

Runner

↓

Hooks

↓

DriverFactory

↓

DriverManager

↓

BasePage

↓

Page Objects

↓

WaitFactory

↓

WebDriver

Each layer has exactly one responsibility.

For example:

DriverFactory only creates browsers.

DriverManager only stores browsers.

ConfigReader only reads configuration.

WaitFactory only manages waits.

This follows the Single Responsibility Principle.

---

## Q4.

Which design patterns have you implemented?

Answer

The current framework uses:

- Singleton
- Factory
- ThreadLocal
- Page Object Model
- Layered Architecture

Each pattern solves a specific engineering problem.

For example:

Singleton prevents multiple ConfigReader objects.

Factory centralizes browser creation.

ThreadLocal enables safe parallel execution.

---

## Q5.

Which SOLID principle is most visible in your framework?

Answer

Single Responsibility Principle (SRP).

Examples

DriverFactory

Only creates browsers.

DriverManager

Only stores browsers.

WaitFactory

Only manages waits.

ConfigReader

Only reads configuration.

BasePage

Only contains reusable Selenium actions.

Every class has one reason to change.

---

## Q6.

Why is your framework scalable?

Answer

Because each responsibility is isolated.

For example,

adding a new browser only changes DriverFactory.

Adding a new page only requires creating a new Page Object.

Adding a new configuration requires only ConfigReader updates.

No unrelated classes need modification.

---

## Q7.

What would you improve in future versions?

Answer

Future versions will include:

- Screenshot Utility
- JavaScript Utility
- Alert Utility
- Frame Utility
- Window Utility
- Dropdown Utility
- Extent Reports
- Log4j2
- TestNG Listeners
- Excel Data Driven Framework
- Parallel Execution
- Docker
- Jenkins

The current architecture has already been designed to support these enhancements without major restructuring.

---

# Chapter 1 Summary

You should now be able to confidently explain:

✔ What an automation framework is.

✔ Why frameworks are required.

✔ Why this framework was built.

✔ Overall framework architecture.

✔ Design patterns used.

✔ SOLID principles.

✔ Scalability of the framework.

---

# Revision Checklist

Before moving to Chapter 2, ensure you can answer the following without looking at the notes.

□ What is an automation framework?

□ Why not write direct Selenium scripts?

□ Explain the architecture.

□ Explain SRP.

□ Explain the design patterns used.

□ Explain why the framework is scalable.

------------------------------------------------------------------------------------------------

---

# Chapter 2

# Java Concepts Used in the Framework

This chapter explains the Java concepts used while building the Smart Selenium Framework.

Every concept is accompanied by examples from the framework itself.

---

## Q1. Why are almost all framework classes declared as `final`?

### Answer

Classes such as:

- DriverFactory
- DriverManager
- FrameworkConstants
- ConfigReader

are declared as `final`.

Example:

```java
public final class DriverFactory {
```

### Why?

These classes are utility classes.

They are not designed to be inherited.

Making them `final` communicates that the class behavior should remain unchanged.

### Advantages

- Prevents accidental inheritance.
- Improves code readability.
- Makes the design intention clear.
- Protects framework architecture.

### Interview Tip

Do **not** say:

> "final improves performance."

That is not the real reason.

The primary reason is **design restriction**.

---

## Q2. Why do these classes have private constructors?

Example:

```java
private DriverFactory() {
}
```

### Why?

Utility classes should never be instantiated.

This would make no sense:

```java
DriverFactory factory = new DriverFactory();
```

DriverFactory only contains static methods.

Creating an object wastes memory and confuses the design.

### Real Example

Instead of

```java
DriverFactory factory = new DriverFactory();
factory.initializeDriver();
```

we simply write

```java
DriverFactory.initializeDriver(browser);
```

### Framework Classes Using Private Constructors

- DriverFactory
- DriverManager
- FrameworkConstants

---

## Q3. Why are methods static?

Example

```java
DriverFactory.initializeDriver(browser);
```

instead of

```java
new DriverFactory().initializeDriver(browser);
```

### Why?

The operation belongs to the class, not to an individual object.

Only one DriverFactory exists conceptually.

There is no state stored inside DriverFactory.

Therefore object creation is unnecessary.

### Advantages

- No object creation.
- Lower memory usage.
- Cleaner syntax.
- Utility class design.

---

## Q4. Why is DRIVER declared as

```java
private static final ThreadLocal<WebDriver> DRIVER;
```

Explain every keyword.

### private

No outside class should directly modify DRIVER.

Only DriverManager controls it.

---

### static

Only one ThreadLocal container should exist.

Every thread stores its own WebDriver inside this shared container.

---

### final

The DRIVER reference should never point to another ThreadLocal object.

This is allowed:

```java
DRIVER.set(driver);
```

This is NOT allowed:

```java
DRIVER = new ThreadLocal<>();
```

---

### ThreadLocal<WebDriver>

Each execution thread receives its own WebDriver instance.

This enables safe parallel execution.

---

## Q5. Why is WebDriver a local variable in DriverFactory?

Example

```java
WebDriver driver;
```

instead of

```java
private static WebDriver driver;
```

### Reason

DriverFactory only creates the browser.

Immediately after creation:

```java
DriverManager.setDriver(driver);
```

stores it inside ThreadLocal.

After that, the local variable is no longer required.

### What happens internally?

```
DriverFactory

↓

WebDriver driver

↓

DriverManager.setDriver(driver)

↓

ThreadLocal

↓

initializeDriver() finishes

↓

Local variable disappears

↓

Browser continues to exist
```

### Why not static WebDriver?

Because:

```
Thread 1

↓

Chrome

Thread 2

↓

Firefox

↓

Chrome reference overwritten
```

Parallel execution breaks.

---

## Q6. Why does ConfigReader use Singleton?

### Problem

Without Singleton:

```java
new ConfigReader();

new ConfigReader();

new ConfigReader();
```

Every object loads

```
config.properties
```

again.

### Solution

One ConfigReader object.

Entire framework shares it.

### Benefits

- Faster execution.
- Less memory.
- Centralized configuration.

---

## Q7. Why is

```java
properties = new Properties();
```

inside the constructor?

### Reason

The constructor executes only once because ConfigReader is Singleton.

Therefore the Properties object is also created only once.

Immediately after creation:

```java
properties.load(fileInputStream);
```

loads the configuration into memory.

Every future request simply reads from memory.

No file reopening occurs.

---

## Q8. Why does getBrowser() return BrowserType instead of String?

Example

```java
public BrowserType getBrowser()
```

instead of

```java
public String getBrowser()
```

### Reason

Returning BrowserType provides compile-time safety.

Example

Instead of

```java
if(browser.equals("chrome"))
```

we use

```java
switch(browser)
```

or

```java
BrowserType.CHROME
```

### Advantages

- No spelling mistakes.
- Cleaner switch statements.
- Better readability.
- Type safety.

---

## Q9. Why use BrowserType.CHROME?

Because BrowserType is an enum.

```java
public enum BrowserType {

    CHROME,
    EDGE,
    FIREFOX

}
```

Each constant is an object of BrowserType.

Therefore

```java
BrowserType.CHROME
```

means

> "Use the CHROME constant defined inside BrowserType."

This is much safer than comparing strings.

---

## Chapter 2 Summary

You should now understand:

✔ final

✔ static

✔ private constructor

✔ Singleton

✔ ThreadLocal

✔ Enum

✔ BrowserType

✔ Local variable

✔ Utility Classes

✔ Compile-time safety

---

## Revision Checklist

Can you explain:

□ Why final?

□ Why private constructor?

□ Why static methods?

□ Why ThreadLocal?

□ Why local WebDriver?

□ Why Singleton?

□ Why Properties inside constructor?

□ Why BrowserType?

□ Why BrowserType.CHROME?

Without looking at the code?

---------------------------------------------------------------------------------------------------
---

# Chapter 3

# Design Patterns Used in the Framework

One of the biggest differences between a Selenium automation engineer and a framework engineer is understanding design patterns.

Our Smart Selenium Framework uses multiple design patterns, each solving a specific engineering problem.

This chapter explains not only the theory but also why each pattern was implemented.

---

# Pattern 1 : Singleton Pattern

Used In

ConfigReader

---

## Interview Question

What is Singleton Pattern?

### Answer

Singleton is a creational design pattern that ensures only one object of a class exists throughout the application's lifecycle.

The object is globally accessible through a single access point.

---

## Why did we use Singleton?

Our framework loads configuration from

```
config.properties
```

If multiple ConfigReader objects are created

```
ConfigReader reader1 = new ConfigReader();

ConfigReader reader2 = new ConfigReader();

ConfigReader reader3 = new ConfigReader();
```

every object will

- Open config.properties
- Read the file
- Allocate memory
- Store duplicate data

This is unnecessary.

Therefore,

```
ConfigReader.getInstance()
```

always returns the same object.

---

## Framework Flow

```
Framework Starts

↓

ConfigReader Object Created

↓

Properties Loaded

↓

Configuration Stored in Memory

↓

Entire Framework Uses Same Object
```

---

## Advantages

✔ Faster

✔ Memory Efficient

✔ Centralized Configuration

✔ Easy Maintenance

---

## Follow-up Interview Questions

Why private constructor?

To prevent

```
new ConfigReader();
```

outside the class.

---

Why static instance?

Because only one shared object should exist.

---

Can Singleton be thread-safe?

Yes.

There are multiple implementations.

Examples

- Synchronized
- Double Checked Locking
- Bill Pugh Singleton

Our framework currently uses Lazy Initialization because configuration loads once during startup.

---

# Pattern 2 : Factory Pattern

Used In

DriverFactory

---

## Interview Question

What is Factory Pattern?

### Answer

Factory Pattern centralizes object creation.

Instead of every class creating its own objects,

one dedicated class becomes responsible for object creation.

---

## Problem Without Factory

Suppose every test writes

```
new ChromeDriver();

new FirefoxDriver();

new EdgeDriver();
```

Browser creation becomes duplicated.

Updating browser configuration becomes difficult.

---

## Our Solution

```
DriverFactory

↓

Creates Browser

↓

Configures Browser

↓

Stores Browser
```

Only DriverFactory creates browser objects.

---

## Responsibilities

- Browser Creation
- Browser Configuration
- Browser Initialization

DriverFactory does NOT

- Store Browser
- Return Thread Driver
- Manage ThreadLocal

Those belong to DriverManager.

---

## Why separate DriverFactory and DriverManager?

Because of

Single Responsibility Principle.

DriverFactory

↓

Creates Browser

DriverManager

↓

Stores Browser

Two different responsibilities.

---

## Advantages

✔ Cleaner Design

✔ Reusable

✔ Easy Browser Addition

✔ Centralized Logic

---

## Interview Follow-up

Suppose tomorrow Safari is added.

Which class changes?

Answer

Only DriverFactory.

---

# Pattern 3 : ThreadLocal Pattern

Used In

DriverManager

---

## Problem

Using

```
static WebDriver driver;
```

Thread 1

↓

Chrome

Thread 2

↓

Firefox

↓

Chrome Reference Lost

Parallel execution fails.

---

## Solution

ThreadLocal

```
ThreadLocal

↓

Thread 1

↓

Chrome

----------------

Thread 2

↓

Edge

----------------

Thread 3

↓

Firefox
```

Each thread owns one browser.

---

## Why static?

Only one ThreadLocal container is required.

---

## Why final?

Reference never changes.

State changes.

---

## Why remove()?

```
remove()
```

cleans the complete thread entry.

Better than

```
set(null)
```

because stale references are removed.

---

## Advantages

✔ Thread Safe

✔ Parallel Execution

✔ No Browser Overwriting

---

## Interview Question

What happens if ThreadLocal is removed?

Parallel execution becomes unstable because multiple threads share the same WebDriver.

---

# Pattern 4 : Page Object Model (POM)

Used In

LoginPage

HomePage

Future Pages

---

## Purpose

Separate business logic from Selenium implementation.

---

## Without POM

```
driver.findElement()

click()

sendKeys()
```

inside Step Definitions.

Very difficult to maintain.

---

## With POM

```
Step Definition

↓

LoginPage

↓

BasePage

↓

WaitFactory

↓

DriverManager
```

Step Definitions become readable.

---

## Responsibilities

Page Objects

- Locators
- Business Actions

Step Definitions

- Business Flow

---

## Why does login() return HomePage?

Instead of

```
login();

void
```

we return

```
HomePage homePage =
login(...);
```

Because successful login always navigates to HomePage.

This is called

Page Transition Pattern.

---

## Advantages

✔ Better OOP

✔ Better Readability

✔ Better Reusability

✔ Easier Navigation

---

# Pattern 5 : Layered Architecture

Our framework is divided into independent layers.

```
Feature

↓

Runner

↓

Hooks

↓

Driver Layer

↓

Page Layer

↓

BasePage

↓

Wait Layer

↓

Selenium
```

Every layer has exactly one responsibility.

Higher layers never directly access lower implementation details.

This reduces coupling.

---

# Relationship Between Patterns

```
Singleton

↓

ConfigReader

------------------------

Factory

↓

DriverFactory

------------------------

ThreadLocal

↓

DriverManager

------------------------

Page Object Model

↓

LoginPage

↓

HomePage

------------------------

Layered Architecture

↓

Entire Framework
```

Every pattern solves a different engineering problem.

---

# Design Pattern Summary

| Pattern | Used In | Purpose |
|----------|----------|----------|
| Singleton | ConfigReader | One Configuration Object |
| Factory | DriverFactory | Browser Creation |
| ThreadLocal | DriverManager | Parallel Execution |
| POM | LoginPage/HomePage | Business Logic Separation |
| Layered Architecture | Entire Framework | Separation of Responsibilities |

---

# Revision Checklist

Before moving to the next chapter, you should be able to answer:

□ What problem does Singleton solve?

□ Why ConfigReader is Singleton?

□ Why DriverFactory is Factory?

□ Why DriverManager uses ThreadLocal?

□ Why login() returns HomePage?

□ Why separate DriverFactory and DriverManager?

□ What is Layered Architecture?

□ Which design pattern is used in each framework class?

If you can answer these confidently without looking at the code, you are ready for the next chapter.

--------------------------------------------------------------------------------------------------

---

# Chapter 4

# Driver Management

Driver Management is the backbone of every Selenium automation framework.

A weak driver management design leads to:

- Browser conflicts
- Memory leaks
- Parallel execution failures
- Difficult maintenance

Our framework separates browser creation from browser storage.

This is one of the most important architectural decisions made in the framework.

---

# Overall Driver Flow

```

@Before Hook

↓

DriverFactory.initializeDriver()

↓

Browser Created

↓

DriverManager.setDriver()

↓

ThreadLocal Stores Browser

↓

Test Execution

↓

DriverManager.getDriver()

↓

Page Objects

↓

Selenium Commands

↓

@After Hook

↓

DriverFactory.quitDriver()

↓

driver.quit()

↓

DriverManager.remove()

```

---

# Q1.

Explain DriverFactory.

## Answer

DriverFactory is responsible only for browser creation.

It knows:

- Which browser to create
- How to configure the browser
- Browser initialization

It does NOT know:

- Which thread owns the browser
- Where the browser is stored
- Which test is executing

Therefore DriverFactory has only one responsibility.

This follows the Single Responsibility Principle.

---

# Q2.

Explain DriverManager.

## Answer

DriverManager stores the WebDriver object for the current thread.

It does not create browsers.

It does not configure browsers.

It only performs three operations:

```

setDriver()

getDriver()

unloadDriver()

```

This separation makes the framework scalable.

---

# Q3.

Why didn't you merge DriverFactory and DriverManager into one class?

## Answer

Although both classes deal with WebDriver, their responsibilities are different.

DriverFactory

Creates browser.

DriverManager

Stores browser.

If one class handled both responsibilities, it would violate the Single Responsibility Principle.

Keeping them separate also makes future maintenance easier.

---

# Interview Conversation

**Interviewer**

Why not write everything inside DriverFactory?

**You**

Because browser creation and browser lifecycle management are different responsibilities.

DriverFactory creates browsers.

DriverManager manages browser references.

Separating them makes the framework easier to maintain and extend.

---

# Q4.

Why does DriverFactory not return WebDriver?

Example

Instead of

```java
WebDriver driver = DriverFactory.initializeDriver();
```

our framework does

```java
DriverFactory.initializeDriver(browser);

DriverManager.getDriver();
```

## Answer

The framework stores the browser inside ThreadLocal immediately after creation.

Every other class accesses the browser through DriverManager.

This ensures that every class receives the browser belonging to the current execution thread.

---

# Q5.

Why is DriverManager static?

## Answer

Only one DriverManager utility is required.

Creating DriverManager objects serves no purpose because it does not maintain instance state.

All browser references are stored inside ThreadLocal.

---

# Q6.

What happens when initializeDriver() executes?

Execution Flow

```

Hooks

↓

DriverFactory.initializeDriver()

↓

switch(browser)

↓

new ChromeDriver()

↓

configureBrowser()

↓

DriverManager.setDriver()

↓

Browser Stored

↓

initializeDriver() Ends

```

Notice that the local variable disappears after the method completes.

The browser continues to exist because ThreadLocal still holds the reference.

---

# Q7.

What happens inside configureBrowser()?

Current Responsibilities

- Maximize Browser
- Delete Cookies
- Set Page Load Timeout

Future Responsibilities

- Browser Options
- Headless Mode
- Incognito Mode
- Download Directory
- Browser Arguments

Keeping browser configuration inside one method makes future updates easy.

---

# Q8.

Why do we call deleteAllCookies() before every test?

## Answer

Every test should start with a clean browser session.

Otherwise previous execution data such as

- Cookies
- Login Sessions
- Remember Me Tokens

may affect current test execution.

This improves test isolation.

---

# Q9.

Why maximize the browser?

## Answer

Different screen resolutions may hide elements or trigger responsive layouts.

Maximizing the browser reduces such inconsistencies and makes execution more stable.

---

# Q10.

Why pageLoadTimeout()?

## Answer

Sometimes a page never finishes loading due to

- Network issues
- Server issues
- Infinite loading

Without a timeout the test could wait indefinitely.

Setting a page load timeout allows the framework to fail gracefully instead of hanging forever.

---

# Q11.

What happens during quitDriver()?

Execution Flow

```

DriverManager.getDriver()

↓

driver.quit()

↓

DriverManager.remove()

↓

Browser Closed

↓

ThreadLocal Entry Removed

↓

Memory Can Be Reclaimed

```

---

# Q12.

Difference between close() and quit()

close()

Closes only the current browser window.

Browser session may continue.

quit()

Closes every browser window.

Ends the WebDriver session.

Releases browser resources.

Frameworks should always use

```

quit()

```

instead of

```

close()

```

---

# Q13.

Why remove() after quit()?

Many candidates answer

"because memory leak."

That answer is incomplete.

Correct Answer

ThreadLocal stores a mapping between

Current Thread

↓

WebDriver

Calling

```java
remove();
```

removes the entire mapping.

Without remove(), long-running parallel executions may retain stale thread entries longer than necessary.

---

# Interview Conversation

**Interviewer**

Suppose you forget remove().

What happens?

**You**

Initially the framework may still work correctly.

However, in long-running executions using thread pools, stale ThreadLocal entries can remain associated with reused threads, increasing memory usage and making resource management harder.

Calling remove() is the recommended cleanup practice.

---

# Q14.

Why BrowserType Enum instead of String?

Old Approach

```java
if(browser.equals("chrome"))
```

Problems

- Typing mistakes
- Case sensitivity
- Hard to maintain

Current Approach

```java
BrowserType.CHROME
```

Advantages

- Compile-time safety
- Cleaner switch statement
- Easier maintenance

---

# Driver Layer Summary

Current Classes

```

DriverFactory

↓

DriverManager

↓

BrowserType

```

Responsibilities

DriverFactory

✔ Browser Creation

DriverManager

✔ Browser Storage

BrowserType

✔ Browser Type Definition

Together they provide a scalable, thread-safe browser management layer.

---

# Revision Checklist

Can you explain without looking?

□ DriverFactory

□ DriverManager

□ ThreadLocal

□ Browser Lifecycle

□ initializeDriver()

□ quitDriver()

□ remove()

□ quit() vs close()

□ BrowserType Enum

□ Driver Layer Architecture

---------------------------------------------------------------------------------

---

# Chapter 5

# Configuration Management

One of the first requirements of an automation framework is to avoid hardcoding values.

Imagine writing the following throughout the project.

```java
driver.get("https://example.com");

String browser = "chrome";

String username = "admin";

String password = "admin123";
```

Now suppose the application URL changes.

You would have to modify multiple classes.

This violates the DRY (Don't Repeat Yourself) principle.

Our framework solves this problem using:

- config.properties
- FrameworkConstants
- ConfigReader (Singleton)

Together they create a centralized configuration management system.

---

# Overall Configuration Flow

```
Framework Starts

↓

FrameworkConstants

↓

ConfigReader

↓

config.properties

↓

Properties Object

↓

Configuration Loaded

↓

Entire Framework Uses Same Object
```

---

# Q1.

Why do we use config.properties?

## Answer

config.properties stores values that may change between environments.

Examples

```
browser

url

username

password

timeouts

headless

screenshot options
```

Instead of hardcoding them inside Java classes, they are stored externally.

Advantages

✔ Easy Maintenance

✔ Environment Switching

✔ Cleaner Code

✔ No Recompilation

---

# Q2.

Why FrameworkConstants?

## Answer

FrameworkConstants stores values that should never change frequently.

Examples

```
PROJECT_ROOT

CONFIG_FILE_PATH

REPORT_PATH

SCREENSHOT_PATH

LOG_PATH
```

These are framework-level constants.

Keeping them in one place avoids duplicate path definitions.

---

# Q3.

Difference between FrameworkConstants and config.properties

FrameworkConstants

Contains

Framework paths

Project directories

Static values

Rarely modified.

--------------------------------

config.properties

Contains

Browser

URL

Credentials

Timeouts

Execution settings

Frequently modified.

---

# Interview Question

Why not put everything inside config.properties?

Answer

Because framework paths are constants.

Changing them frequently is not expected.

Configuration values such as browser and URL change much more often.

Therefore they belong in config.properties.

---

# Q4.

Why ConfigReader is Singleton?

Without Singleton

```
new ConfigReader();

new ConfigReader();

new ConfigReader();
```

Every object opens

config.properties

again.

This causes

Repeated file access

Repeated memory allocation

Repeated parsing

Instead

```
ConfigReader.getInstance()
```

creates one object.

Loads configuration once.

Entire framework reuses it.

---

# Q5.

Explain ConfigReader constructor.

Current Implementation

```java
private ConfigReader() {

    properties = new Properties();

    try(FileInputStream fileInputStream =
        new FileInputStream(
        FrameworkConstants.CONFIG_FILE_PATH)) {

        properties.load(fileInputStream);

    }

}
```

Execution

```
Constructor

↓

Create Properties Object

↓

Open File

↓

Load Configuration

↓

Store in Memory

↓

Close File

↓

Constructor Ends
```

Configuration now remains available throughout framework execution.

---

# Q6.

Why is

```java
properties = new Properties();
```

inside constructor?

Answer

Because constructor executes only once.

Singleton guarantees

One Object

↓

One Constructor Call

↓

One Properties Object

↓

One File Read

Everything after that uses the same Properties object.

---

# Interview Conversation

Interviewer

Why didn't you make Properties static?

You

Because Properties belongs to the ConfigReader object.

Singleton already guarantees only one ConfigReader instance.

Making Properties static provides no additional benefit.

Keeping it as an instance member maintains proper object-oriented design.

---

# Q7.

Why FileInputStream?

Answer

config.properties is a physical file stored inside the framework.

FileInputStream provides a stream that allows Java to read bytes from the file.

Those bytes are interpreted by

```
properties.load()
```

which converts them into key-value pairs.

---

# Q8.

What happens after properties.load()?

Example

config.properties

```
browser=chrome

url=https://example.com
```

Memory Representation

```
Properties

↓

browser

↓

chrome

-------------------

url

↓

https://example.com
```

Whenever framework calls

```java
properties.getProperty("browser")
```

the value is returned directly from memory.

The file is NOT reopened.

---

# Q9.

Why create getValue()?

Instead of writing

```java
properties.getProperty()
```

inside every method,

we created

```java
private String getValue(String key)
```

Advantages

- Reusable
- Cleaner
- Easier maintenance

If property retrieval changes later,

only one method changes.

---

# Q10.

Why getBrowser() returns BrowserType?

Current Code

```java
public BrowserType getBrowser()
```

instead of

```java
public String getBrowser()
```

Advantages

Compile-time Safety

Cleaner switch

No spelling mistakes

Enum support

Better readability

---

# Q11.

Explain BrowserType.valueOf()

Current Code

```java
BrowserType.valueOf(

getValue("browser")

.trim()

.toUpperCase()

);
```

Execution

```
Read browser

↓

Remove Spaces

↓

Convert To Uppercase

↓

Find Matching Enum

↓

Return BrowserType
```

Suppose

config.properties

contains

```
browser=chrome
```

Execution

```
chrome

↓

CHROME

↓

BrowserType.CHROME
```

---

# Interview Question

Why toUpperCase()?

Answer

Enums are case-sensitive.

```
BrowserType.CHROME
```

exists.

```
BrowserType.chrome
```

does not.

Therefore we convert to uppercase before calling valueOf().

---

# Q12.

Why parseInt()?

Current Code

```java
Integer.parseInt(

getValue("page.load.timeout")

)
```

Properties always return

String

We convert String

↓

Integer

so Selenium APIs can use numeric values.

---

# Q13.

Why parseBoolean()?

Current Code

```java
Boolean.parseBoolean(

getValue("headless")

)
```

Properties

```
headless=true
```

returns String.

Framework converts it into boolean.

Now code becomes

```java
if(ConfigReader.getInstance().isHeadless())
```

instead of

```java
if(getValue("headless").equals("true"))
```

Cleaner.

Safer.

---

# Q14.

Why trim()?

Suppose someone writes

```
browser = chrome
```

Notice the spaces.

trim()

removes unnecessary leading and trailing spaces before processing.

This prevents unexpected errors.

---

# Q15.

What happens if config.properties cannot be loaded?

Current Code

```java
throw new RuntimeException(

"Unable to load config.properties"

);
```

Reason

Framework cannot continue without configuration.

Instead of producing multiple NullPointerExceptions later,

it fails immediately with a meaningful message.

This is called

Fail Fast Principle.

---

# Configuration Layer Summary

Classes

```
FrameworkConstants

↓

ConfigReader

↓

Properties

↓

config.properties
```

Responsibilities

FrameworkConstants

Framework paths.

ConfigReader

Reads configuration.

Properties

Stores configuration in memory.

config.properties

Contains configurable values.

---

# Revision Checklist

Can you explain without looking?

□ FrameworkConstants

□ ConfigReader

□ config.properties

□ Singleton

□ Properties

□ FileInputStream

□ getValue()

□ BrowserType.valueOf()

□ parseInt()

□ parseBoolean()

□ trim()

□ Fail Fast Principle


---------------------------------------------------------------------------------------------------

---

# Chapter 6

# Wait Strategy and BasePage Architecture

One of the biggest reasons automation frameworks become unstable is improper synchronization.

A test script is often much faster than the web application.

If Selenium tries to interact with an element before it is ready, the test fails.

To solve this problem, our framework introduces a dedicated Wait Layer.

Current Wait Architecture

```
Page Object

↓

BasePage

↓

WaitFactory

↓

WebDriverWait

↓

Selenium
```

Every Page Object uses the same waiting mechanism.

This ensures consistency throughout the framework.

---

# Q1.

Why do we need WaitFactory?

## Answer

Without WaitFactory every Page Object would contain code like

```java
WebDriverWait wait =
new WebDriverWait(driver, Duration.ofSeconds(20));

wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
```

This code would be repeated everywhere.

Instead,

all waiting logic is centralized inside WaitFactory.

Advantages

✔ Reusable

✔ Easy maintenance

✔ One place to update waiting logic

✔ Cleaner Page Objects

---

# Q2.

What is WaitFactory responsible for?

Current Responsibilities

- Explicit Wait creation
- Waiting for element visibility
- Waiting for element clickability

Future Responsibilities

- Presence of element
- Invisibility
- Alert waiting
- Frame waiting
- URL waiting
- Title waiting
- Number of windows
- Custom ExpectedConditions

WaitFactory should contain only synchronization logic.

Nothing else.

---

# Q3.

Why didn't we create WebDriverWait inside every page?

Example

Without WaitFactory

```java
LoginPage

↓

new WebDriverWait()

↓

click()
```

HomePage

↓

new WebDriverWait()

↓

click()

Again duplicated.

Instead

```
LoginPage

↓

WaitFactory

↓

WebDriverWait
```

One implementation.

Entire framework shares it.

---

# Interview Conversation

Interviewer

Why not create one global WebDriverWait object?

You

Because waits depend on WebDriver and sometimes on timeout values.

Creating them inside WaitFactory keeps the implementation centralized while allowing flexibility if different waits are required in future.

---

# Q4.

Difference between Implicit Wait and Explicit Wait.

Implicit Wait

Applies globally.

Selenium waits while locating elements.

Cannot wait for specific conditions.

---

Explicit Wait

Waits for a particular condition.

Examples

- Visible
- Clickable
- Invisible
- Alert
- Frame
- URL

Our framework uses Explicit Wait because it is more precise and easier to control.

---

# Q5.

Why do modern frameworks prefer Explicit Wait?

Advantages

- Faster execution
- Better synchronization
- More reliable tests
- Less flaky automation

Explicit Wait waits only when necessary.

Implicit Wait affects every element lookup.

---

# Q6.

Why BasePage?

Without BasePage

Every Page Object would repeat

```java
click()

sendKeys()

getText()

clear()

isDisplayed()
```

This violates DRY.

Instead

```
BasePage

↓

Reusable Selenium Actions

↓

LoginPage

↓

HomePage

↓

Future Pages
```

Every page inherits common functionality.

---

# Q7.

Responsibilities of BasePage

BasePage should contain only reusable Selenium operations.

Examples

```
click()

enterText()

clear()

getText()

isDisplayed()

openApplication()
```

It should NOT contain business logic.

---

# Q8.

What belongs inside Page Objects?

Page Objects should contain

- Locators
- Business actions

Example

```java
login()

logout()

searchProduct()

transferFunds()
```

They should not contain

- Browser initialization
- Thread management
- Configuration loading
- Report generation

---

# Q9.

Why did we move from PageFactory to By locators?

Initially many Selenium frameworks used

```java
@FindBy
```

and

```java
PageFactory.initElements()
```

Although still supported, modern Selenium projects increasingly prefer `By` locators because they are:

- Simpler
- Easier to debug
- More explicit
- Better suited for dynamic pages
- Independent of PageFactory initialization

Our framework was refactored to use the `By` strategy.

---

# Interview Question

Is PageFactory deprecated?

Answer

No.

PageFactory is **not officially deprecated** in Selenium.

However, it is no longer actively enhanced, and most modern frameworks prefer the `By` locator approach because it is simpler and offers greater control.

Avoid saying:

"PageFactory is removed."

That would be incorrect.

---

# Q10.

Why By locators?

Example

```java
private final By username =
By.id("username");
```

Advantages

- Cleaner
- Reusable
- Easier debugging
- Dynamic locator support
- Works naturally with Explicit Wait

---

# Q11.

Why login() returns HomePage?

Old Approach

```java
login();

void
```

Current Approach

```java
HomePage homePage =
login(username,password);
```

Reason

Successful login navigates to HomePage.

Returning the next Page Object represents the application's navigation flow.

This is known as the **Page Transition Pattern**.

Advantages

- Better Object-Oriented Design
- Readable Test Flow
- Easier chaining of actions
- Industry best practice

---

# Q12.

Why do Step Definitions contain very little code?

Example

```java
loginPage.open();

HomePage homePage =
loginPage.login(username,password);

Assert.assertTrue(
homePage.isLoginSuccessful());
```

Reason

Step Definitions describe business flow.

They should not contain Selenium implementation.

All Selenium operations belong inside Page Objects and BasePage.

---

# Interview Conversation

Interviewer

Where should Selenium code exist?

You

Inside BasePage and Page Objects.

Step Definitions should only coordinate business actions.

This keeps the framework maintainable and follows Separation of Concerns.

---

# Q13.

Why openApplication() inside BasePage?

Instead of

```java
DriverManager.getDriver().get(url);
```

inside every page,

BasePage provides

```java
openApplication(url);
```

Advantages

- Reusable
- Cleaner
- Single implementation
- Easy future enhancements

For example, logging or reporting can later be added in one place.

---

# Q14.

How do all these classes work together?

Execution Flow

```
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

↓

Browser
```

Every class has one responsibility.

This is the biggest architectural strength of the framework.

---

# Chapter 6 Summary

Current Classes

```
WaitFactory

↓

BasePage

↓

LoginPage

↓

HomePage
```

Responsibilities

WaitFactory

✔ Synchronization

BasePage

✔ Common Selenium Actions

LoginPage

✔ Login Business Actions

HomePage

✔ Home Page Business Actions

Together they create a clean, reusable Page Object architecture.

---

# Revision Checklist

Can you explain without looking?

□ Why WaitFactory?

□ Explicit vs Implicit Wait

□ Why BasePage?

□ Responsibilities of BasePage

□ Responsibilities of Page Objects

□ Why By locators?

□ Is PageFactory deprecated?

□ Why login() returns HomePage?

□ What is the Page Transition Pattern?

□ Why keep Step Definitions small?

□ Overall execution flow from Step Definition to Browser.

-------------------------------------------------------

---

# Chapter 7

# Real Framework Interview Discussion

This chapter contains real interview discussions based on the Smart Selenium Framework.

Unlike previous chapters, these are not direct questions.

Instead, they simulate an actual technical interview.

Try answering each question yourself before reading the suggested answer.

---

# Interview 1

Interviewer

Can you explain your framework architecture?

Candidate

Certainly.

Our framework follows a layered architecture where each layer has a single responsibility.

Execution starts from the Feature File.

The Runner starts Cucumber execution.

Hooks create the browser before every scenario.

DriverFactory creates the browser.

DriverManager stores the browser using ThreadLocal.

Page Objects perform business actions.

BasePage provides reusable Selenium operations.

WaitFactory manages synchronization.

Finally Selenium communicates with the browser.

This separation makes the framework scalable and easy to maintain.

---

# Interview 2

Interviewer

Why did you create DriverFactory?

Candidate

Browser creation should happen in only one place.

If every test creates its own browser, browser configuration becomes duplicated.

DriverFactory centralizes browser creation while DriverManager manages browser storage.

This follows the Single Responsibility Principle.

---

# Interview 3

Interviewer

Why not make DriverFactory Singleton?

Candidate

DriverFactory only contains static utility methods.

It has no object state.

Creating a Singleton object would not provide any additional benefit.

Singleton is useful when object state must be shared.

DriverFactory has no such requirement.

---

# Interview 4

Interviewer

Why ConfigReader Singleton but DriverFactory is not?

Candidate

ConfigReader stores configuration.

That configuration should be loaded only once.

DriverFactory simply creates browsers.

It does not maintain state.

Therefore Singleton is meaningful for ConfigReader but unnecessary for DriverFactory.

---

# Interview 5

Interviewer

Why ThreadLocal?

Candidate

Without ThreadLocal, all parallel tests would share one WebDriver object.

One thread could overwrite another thread's browser reference.

ThreadLocal ensures each execution thread owns its own WebDriver instance.

This makes parallel execution safe.

---

# Interview 6

Interviewer

What happens internally after initializeDriver() finishes?

Candidate

DriverFactory creates a local WebDriver variable.

Immediately after creation, it stores that object inside DriverManager.

DriverManager stores it inside ThreadLocal.

After initializeDriver() finishes, the local variable disappears.

The browser continues to exist because ThreadLocal still holds the reference.

---

# Interview 7

Interviewer

Why BrowserType Enum?

Candidate

Enums provide compile-time safety.

Instead of comparing Strings like

browser.equals("chrome")

we use

BrowserType.CHROME

This eliminates spelling mistakes and makes switch statements cleaner.

---

# Interview 8

Interviewer

Why did you remove PageFactory?

Candidate

PageFactory still works.

However, modern Selenium frameworks generally prefer By locators because they are simpler, easier to debug, and integrate naturally with explicit waits.

Our framework therefore uses By locators instead of PageFactory.

---

# Interview 9

Interviewer

What would happen if ConfigReader created a new object every time?

Candidate

Every object would reopen config.properties.

The same configuration would be loaded repeatedly.

This wastes memory and increases unnecessary file operations.

Singleton solves this by loading the configuration only once.

---

# Interview 10

Interviewer

Suppose tomorrow your manager asks you to add Microsoft Edge support.

Which classes will change?

Candidate

Only DriverFactory.

BrowserType enum will receive one additional constant if required.

No Page Objects, Step Definitions, Hooks or BasePage require modification.

This demonstrates low coupling.

---

# Interview 11

Interviewer

Which class is the heart of your framework?

Candidate

There is no single heart.

Every layer has a responsibility.

However,

DriverManager,

ConfigReader,

BasePage,

WaitFactory

are among the most reusable components because almost every test depends on them.

---

# Interview 12

Interviewer

What are you most proud of in this framework?

Candidate

The architectural separation.

Every class has exactly one responsibility.

Browser management, configuration management, synchronization, page actions and business flow are completely separated.

This makes future maintenance much easier than having everything inside test classes.

---

# Self Assessment

Can you answer every interview without looking?

□ Framework Architecture

□ DriverFactory

□ DriverManager

□ ThreadLocal

□ Singleton

□ ConfigReader

□ BrowserType

□ BasePage

□ WaitFactory

□ By Locator Strategy

□ Page Transition Pattern

If yes, you are ready to explain the current version of the framework confidently.

-------------------------------------------------------------------------------------