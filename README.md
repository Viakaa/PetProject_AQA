# PetProject_AQA

## Overview

**PetProject_AQA** is a **Java-based test automation framework** for **Web UI**, **API**, and **performance testing**.  
It uses **Selenium WebDriver**, **TestNG**, **Rest-Assured**, **Hibernate**, **Allure**, **JMeter**, and is integrated with **Jenkins** for CI/CD.

The framework follows a **layered architecture** and is designed for **scalability**, **maintainability**, and **cross-browser testing**.

---

## Project Structure

AQA
├── allure-results # Allure report results (screenshots, API JSONs, test logs)
├── jenkins # Jenkins job configurations (CI/CD pipelines)
├── logs # Logs of test executions (UI, API, performance)
├── performance # JMeter test plans and results for performance testing
├── src
│ ├── main
│ │ ├── java
│ │ │ └── aqa
│ │ │ ├── api
│ │ │ │ ├── ExtractResponse.java # POJO class for Extract API responses
│ │ │ │ ├── RandomResponse.java # POJO class for Random Article API responses
│ │ │ │ └── SearchResponse.java # POJO class for Search API responses
│ │ │ │
│ │ │ ├── bo
│ │ │ │ ├── HomeBO.java # Business Object layer for Home page UI actions
│ │ │ │ └── SearchBO.java # Business Object layer for Search page UI actions
│ │ │ │
│ │ │ ├── db
│ │ │ │ ├── HibernateUtil.java # Utility class for Hibernate session management
│ │ │ │ └── TestData.java # ORM class for test data generation & validation
│ │ │ │
│ │ │ ├── listeners
│ │ │ │ ├── CustomAllureListener.java # Listener for Allure reporting
│ │ │ │ └── CustomListener.java # Custom TestNG listener for logging and reporting
│ │ │ │
│ │ │ ├── po
│ │ │ │ ├── HomePage.java # Page Object for Home page
│ │ │ │ └── SearchResultsPage.java # Page Object for Search Results page
│ │ │ │
│ │ │ ├── ConfigReader.java # Reads configuration properties (e.g., base URL, browser)
│ │ │ └── DriverPool.java # Manages WebDriver instances for multiple browsers (Chrome, Firefox)
│ │ │
│ │ └── resources
│ │ └── testng.xml # TestNG suite configuration (UI & API tests, listeners, groups)
│ │
│ └── test
│ └── java
│ └── aqa
│ ├── api
│ │ ├── ExtractIntroApiTest.java # API test for extracting intro from Wikipedia
│ │ ├── RandomArticleInfoApiTest.java # API test for random article info
│ │ └── SearchArticleApiTest.java # API test for searching articles
│ │
│ └── ui
│ ├── TestLinkPresent.java # UI test checking presence of links
│ ├── TestSearch.java # UI test for search functionality
│ └── TestSearchResultHeading.java # UI test verifying search result headings
│
├── target # Maven build output (compiled classes, reports)
└── pom.xml # Maven project configuration (dependencies, plugins)


### Web UI Testing
- Multi-browser support (**Chrome** & **Firefox**) using `DriverPool`.
- Layered architecture: **TC -> BO -> PO**.
- Uses **PageFactory** and **WebElement wrappers**.
- Screenshots automatically captured on test failure.
- At least **3 end-to-end UI test scenarios**.

### API Testing
- Tests follow **TC -> BO** pattern.
- API responses deserialized into **POJOs** for easy assertions.
- At least **3 API test scenarios** with object wrappers.
- JSON responses are attached in **Allure reports**.

### Performance Testing
- **JMeter** integration with at least **3 ThreadGroups** simulating different loads.

### Logging & Reporting
- **Allure reports** with:
  - Screenshots for UI tests.
  - JSON attachments for API responses.
- Logging using **Log4j / SLF4J**.

### CI/CD Integration
- **Jenkins jobs** for:
  - Web UI testing
  - API testing
  - Performance testing

  
