package GoRestUsers;

import POJO.Users;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UsersTest extends RandomStringGenerator {

    @Test // Tested with Postman manually and reported using Newman.
    public void getAllUsersTest() {
        baseURI = "https://gorest.co.in/public/v2/users";
        given()
                .when()
                .get(baseURI)
                .then()
                .log().body() // Prints the response body to the console
                .body("[0].name", equalTo("Anunay Kaniyar")) // Test if first users name is the same as the expected
                .body("data", not(empty())) // Check that the array is not empty
                .body("data", hasSize(lessThanOrEqualTo(10))) // Check that the array size is as expected
                .contentType(ContentType.JSON) // Tests if the response body is in JSON format
                .log().status() // Prints the status of the request
                .statusCode(200); // Tests if the status code is the same as the expected

    }
    @Test
        // Tested with Postman manually and reported using Newman.
    void queryParamTest() { // If the parameter is separated by " ? " it is called query parameter
        given()
                .when()
                .log().uri()
                .param("page", 1) // https://gorest.co.in/public/v2/users?page=1
                .pathParam("APIName", "users")
                .get("https://gorest.co.in/public/v1/{APIName}")
                .then()
                .log().body()
                .statusCode(200)
                .body("meta.pagination.page", equalTo(1));
    }
    @Test // This test for only automated execution.
    public void paginationPageNumberConsistencyTest() {
        for (int i = 1; i <= 10; i++) {
            given()
                    .when()
                    .log().uri()
                    .param("page", i)
                    .pathParam("APIName", "users")
                    .get("https://gorest.co.in/public/v1/{APIName}")
                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("meta.pagination.page", equalTo(i));
        }
    }
    @DataProvider
    public Object[][] pageNumbers() {

        Object[][] pageNumberList = {{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}};

        return pageNumberList;
    }
    @Test(dataProvider = "pageNumbers")  // This test for only automated execution.
    public void paginationPageNumberConsistencyWithDataProviderTest(int page) {
        given()
                .when()
                .log().uri()
                .param("page", page)
                .pathParam("APIName", "users")
                .get("https://gorest.co.in/public/v1/{APIName}")
                .then()
                .log().body()
                .statusCode(200)
                .body("meta.pagination.page", equalTo(page));
    }

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    void setUpSpecification() {
        baseURI = "https://gorest.co.in/public/v2/users";

        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer ed5d0582b475bb6a801efb4bd9ec0e682fa9a3fa8f5e028d55608a07224c3eb4")
                .setContentType(ContentType.JSON)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .expectContentType(ContentType.JSON)
                .build();

    }

    @Test
    void createUserWithMaps() {

        Map<String, String> user = new HashMap<>();
        user.put("name", randomName());
        user.put("email", randomEmail());
        user.put("gender", "female");
        user.put("status", "active");

        given()
                .spec(requestSpecification)
                .body(user)
                .when()
                .post(baseURI)
                .then()
                .statusCode(201)
                .spec(responseSpecification)
                .body("email", equalTo(user.get("email")));
    }

    Users users;
    Response responseBody;

    @Test(priority = 1)
    void createNewUserWithPojo() {
        users = new Users();
        users.setName(randomName());
        users.setGender("male");
        users.setEmail(randomEmail());
        users.setStatus("active");

        responseBody = given()
                .spec(requestSpecification)
                .body(users)
                .when()
                .post()
                .then()
                .statusCode(201)
                .spec(responseSpecification)
                .body("email", equalTo(users.getEmail()))
                .extract().response();
    }

    @Test(dependsOnMethods = "createNewUserWithPojo", priority = 1)
    void createUserNegativeTest() {
        users.setName(randomName());
        users.setGender("female");
        given()
                .spec(requestSpecification)
                .body(users)
                .when()
                .post("")
                .then()
                .statusCode(422)
                .spec(responseSpecification)
                .body("[0].message", equalTo("has already been taken"));
    }

    @Test(dependsOnMethods = "createNewUserWithPojo", priority = 1)
    void updateUserWithPojo() {
        users.setName(randomName());
        given()
                .spec(requestSpecification)
                .pathParam("userId", responseBody.path("id"))
                .body(users)
                .when()
                .put("/{userId}")
                .then()
                .log().body()
                .statusCode(200)
                .spec(responseSpecification)
                .body("email", equalTo(users.getEmail()))
                .body("name", equalTo(users.getName()));
    }

    @Test(dependsOnMethods = "createNewUserWithPojo", priority = 2)
    void deleteUser() {
        given()
                .spec(requestSpecification)
                .pathParam("userId", responseBody.path("id"))
                .when()
                .delete("/{userId}")
                .then()
                .statusCode(204);
    }

    @Test(dependsOnMethods = {"createNewUserWithPojo", "deleteUser"}, priority = 2)
    void deleteUserNegativeTest() {
        given()
                .spec(requestSpecification)
                .pathParam("userId", responseBody.path("id"))
                .when()
                .delete("/{userId}")
                .then()
                .statusCode(404);
    }
}



