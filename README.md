# Rest_Assured_API_Testing

## Overview

This project focuses on automating the testing of a RESTful API using the RestAssured framework in Java. It employs the TestNG testing framework for Java. The primary objective is to ensure the robustness and reliability of the REST API by automating various test scenarios.

### Demo

1. **Postman Test**
 
   ![Postman Test](https://github.com/ozgurgogersin/Rest_Assured_API_Testing/blob/master/src/test/java/_Gifs/postman.gif)

2. **Newman Test & HTMLExtra Report**
 
   ![Newman Test](https://github.com/ozgurgogersin/Rest_Assured_API_Testing/blob/master/src/test/java/_Gifs/newman.gif)

3. **RestAssured Test**
 
   ![RestAssured](https://github.com/ozgurgogersin/Rest_Assured_API_Testing/blob/master/src/test/java/_Gifs/restAssured.gif)

## Testing Framework and Tools

### Automation Tools:

- **RestAssured:**
  - A powerful testing framework for REST APIs in Java. It simplifies the testing of HTTP-based services, making it an excellent choice for API automation testing.

- **TestNG:**
  - A testing framework for Java that supports various testing approaches, including unit, functional, and integration testing. TestNG provides annotations for better test structure and parallel execution, enhancing the automation testing process.

- **Newman:**
  - Newman is a command-line collection runner for Postman, a comprehensive API development environment. It facilitates the automated execution of Postman collections via the command line, making it a valuable tool for API automation testing.

### Manual Testing Tools:

- **Postman:**
  - A user-friendly API development environment with comprehensive testing capabilities. It allows for manual testing, creation of API requests, and validation of API functionality through a graphical interface.

## Manual Testing

Before automation, the project underwent thorough manual testing using Postman to validate the correctness of API endpoints and responses. This manual testing process involved:

1. **Postman Tests:**
   - API endpoints were manually tested using Postman to ensure proper functionality.

2. **Reporting with Newman HTML Extra:**
   - Postman collections were executed through Newman, the command-line collection runner.
   - Newman HTML Extra reports were generated to provide detailed insights into the test results.
   - These reports were manually reviewed and analyzed for identifying any issues or unexpected behavior.

3. **Newman HTML Extra Report:**
   - Include a brief description of how to generate and access the Newman HTML Extra report. For example:
   - To generate the Newman HTML Extra report, run the following command in the terminal:
     ```
     newman run { your_collection } -r htmlextra
     ```

## Automated Testing Approaches

The automated testing includes various scenarios to validate the functionality and reliability of the REST API.

### - Data-Driven Testing

- The project embraces data-driven testing to enhance the efficiency and coverage of test scenarios. 
- An illustrative example is the `paginationPageNumberConsistencyWithDataProviderTest` method, which utilizes TestNG's `@DataProvider` annotation.

### - POJO (Plain Old Java Object) Usage

- The project leverages POJOs to model API responses and requests, enhancing code readability and maintainability.

### - CRUD Operations

- **Create:**
  - Demonstrates creating a new user using maps and POJOs.

- **Read:**
  - Tests retrieving all users and pagination consistency.

- **Update:**
  - Updates a user's information using POJOs.

- **Delete:**
  - Deletes a user and tests negative scenarios.
