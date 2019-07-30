package com.splitwise.splitwise.controller;

import static io.restassured.RestAssured.given;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.splitwise.splitwise.model.request.LoginRequest;
import com.splitwise.splitwise.model.request.RegisterUserRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLoginRegisterTest {

    @Autowired
    private UserController userController;

    @Before
    public void name() {
        RestAssured.baseURI =  "http://localhost";
        RestAssured.port = 8890;
    }

    /**
     * test_AddUsers
     */
    @Test
    public void a() {

        final RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setName("Atul");
        registerUserRequest.setEmail("ak@gmail.com");
        registerUserRequest.setPassword("123");

        final Response response = given()
                .contentType("application/json")
                .accept("application/json")
                .body(registerUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(response.asString());
    }

    /**
     * test_login
     */
    @Test
    public void b() {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("ak@gmail.com");
        loginRequest.setPassword("123");
        final Response response = given()
                .contentType("application/json")
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then().statusCode(200)
                .extract()
                .response();
        System.out.println(response.asString());
    }
}
