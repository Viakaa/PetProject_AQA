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

##UI Automation Scenarios

**Goal:** Verify that a user can log in, access a protected page (Watchlist), and log out successfully.
1.  **Open** the Wikipedia Login page.
2.  **Enter** valid credentials (username and password).
3.  **Click** the "Log in" button.
4.  **Navigate** to the "Watchlist" page.
5.  **Verify** the page title contains "Watchlist".
6.  **Click** the "Log out" button.
7.  **Verify** the user is redirected to the Logout confirmation or Homepage.

### 2. Search and External Link Navigation (`TestArticlesLink`)
**Goal:** Verify search functionality, article rendering, and navigation to external official sites.
*Data Source: Parameterized search terms.*
1.  **Open** the Homepage.
2.  **Type** a search term into the search bar and submit.
3.  **Click** on the specific article result.
4.  **Verify** the Article Heading matches the expected title.
5.  **Click** the external link (e.g., "Official website") within the article.
6.  **Verify** the current URL matches the expected external domain.

### 3. Random Article Language Switch (`TestRandomArticleSwitchLanguage`)
**Goal:** Verify that switching languages on a random article redirects to the correct URL.
1.  **Log in** to the application.
2.  **Click** "Random Article" in the navigation menu.
3.  **Capture** the title and URL of the opened article.
4.  **Click** the first available language link in the sidebar.
5.  **Capture** the expected URL from the link and the actual URL after redirection.
6.  **Verify** that the URL has changed from the initial one.
7.  **Verify** that the current URL matches the specific language link that was clicked.

### API Testing
- Tests follow **TC -> BO** pattern.
- API responses deserialized into **POJOs** for easy assertions.
- At least **3 API test scenarios** with object wrappers.
- JSON responses are attached in **Allure reports**.

##API Automation Scenarios

### 1. Create User Sandbox Page (`CreateSandboxPageTest`)
**Goal:** Verify the API capability to create or edit a Wiki page using a CSRF token.
1.  **POST** request to retrieve a **Login Token**.
2.  **POST** request to perform **Login** using credentials and Login Token.
3.  **GET** request to retrieve a **CSRF Token** (`type=csrf`).
4.  **POST** request to `action=edit`:
    *   **Target:** `User:<username>/sandbox/TestPage_Check`
    *   **Content:** "Automated test create via RestAssured."
5.  **Verify** response status is "Success".
6.  **GET** request to `action=query` for the created page.
7.  **Verify** the returned page content matches the text sent in step 4.

### 2. Change User Profile Option (`ChangeUsersGenderApiTest`)
**Goal:** Verify the API capability to modify user preferences.
1.  **POST** request to retrieve a **Login Token**.
2.  **POST** request to perform **Login**.
3.  **GET** request to retrieve a **CSRF Token**.
4.  **POST** request to `action=options`:
    *   **Option Name:** `gender`
    *   **Option Value:** `female`
5.  **Verify** response status is "Success".
6.  **GET** request to `action=query&meta=userinfo`.
7.  **Verify** the `gender` attribute in the user profile XML response matches `female`.

### 3. Add Page to Watchlist (`AddPageToWatchListApiTest`)
**Goal:** Verify the API capability to add a specific page to the user's watchlist.
1.  **POST** request to retrieve a **Login Token**.
2.  **POST** request to perform **Login**.
3.  **GET** request to retrieve a **Watch Token** (`type=watch`).
4.  **POST** request to `action=watch`:
    *   **Target:** `User:<username>/sandbox/TestPage_Watchlist`
5.  **Verify** the response confirms the page is watched.
6.  **GET** request to `action=query` with `inprop=watched` for the specific page.
7.  **Verify** the page XML data contains the `watched` attribute, confirming it is tracked by the user.

### Performance Testing
- **JMeter** integration with at least **3 ThreadGroups** simulating different loads.

##Perfomance Scenerios

### 1. Authentication & Session Management
**Goal:** Establish a valid session with the MediaWiki API.
1.  **Open Main Page:** Simulates a user visiting the homepage (`/wiki/Main_Page`) to initialize cookies.
2.  **Request Login Token:** Sends a POST request to `action=login` to retrieve the initial authentication token.
3.  **Perform Login:** authenticates the user using the retrieved token and credentials from `users.csv`.
4.  **Get CSRF Token:** Retrieves the `csrftoken` required for all subsequent write operations (edits, creations).

### 2. Content Management (Sandbox)
**Goal:** Measure latency and success rates for page creation and editing.
1.  **Create Sandbox Page:** Uses `action=edit` with `createonly=1` to generate a new page under `User:<username>/sandbox/<title>`.
2.  **Edit Page:** Appends text to a sandbox page (using `updatedTitle`) to simulate content updates.
3.  **Read User Page:** Uses `action=parse` to retrieve and verify the content of the edited page.

### 3. Watchlist Management
**Goal:** Test the performance of the user's personal watchlist API.
1.  **Get Watch Token:** Retrieves the specific security token required for watchlist operations.
2.  **Add to Watchlist:** Adds the previously manipulated sandbox page to the user's watchlist.
3.  **Remove from Watchlist:** Sends a request with `unwatch=1` to remove the same page, cleaning up the state.

### 4. Discussion & Social Interaction
**Goal:** Simulate user interaction on Talk pages.
1.  **Create Discussion Thread:** Posts a new message to `User_talk:<username>/TestThread` with a timestamp.
2.  **Append to Discussion:** Adds a second message ("Another message from JMeter") to the existing thread to simulate a reply or follow-up.

##How to run test report
- src/performance/Testing_plan_WebShop.jmx. To run: jmeter -n -t src/performance/Testing_plan_WebShop.jmx -l results.jtl. Results will be saved to results.jtl. You can open them in JMeter GUI or export as CSV/HTML for analysis.

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

