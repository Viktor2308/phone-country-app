# Country Identifier By Phone Number App

## Description
This project is a phone number country identifier application. It determines the country of a given phone number based on country calling codes from the Wikipedia page.



## Instructions for Running Application
### Prerequisites
Install  on your system:
git, Java 17, maven, docker

- clone the repository using Git
- navigate to project directory using command: cd your-repository
- build the project: mvn clean install
- run all tests: mvn test (for more detail tests command: mvn test -X)

### Quick Start Guide for Running the Application with Docker

- Clone the repository using Git
- Build the JAR File: mvn clean package
- Run Docker Compose: docker-compose up --build
- Access the Application: http://localhost:8088/index.html (Note: An active internet connection is required as the database is populated automatically at startup.)
- Stop the Containers: docker-compose down


## Functional Requirements
- Phone Number Validation and Country Detection: The user inputs a phone number. The application validates the number and identifies the country. If the country is successfully identified, the country's name is displayed. Otherwise, an error message is shown.
- Country Matching: The application should determine the most appropriate country based on the phone number. For example:
- For the number 12423222931, the expected country is "Bahamas".
- For the number 11165384765, the expected countries are "United States, Canada".
- For the number 71423423412, the expected country is "Russia".
- For the number 77112227231, the expected country is "Kazakhstan".
- Data Loading: The country calling code table must be loaded each time the application starts.
- RESTful API: All interactions with the application are performed through a RESTful API with JSON as the data format.
- Server Port: The server should run on port 8088.
### Backend
- Java 17
- Spring Boot 3.3.2
- PostgreSQL
- Maven
### Frontend
- HTML
- CSS
- JavaScript
