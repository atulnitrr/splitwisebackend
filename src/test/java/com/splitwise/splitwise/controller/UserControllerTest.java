package com.splitwise.splitwise.controller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import com.splitwise.splitwise.model.request.AddGroupRequest;
import com.splitwise.splitwise.model.request.UserGroupRequest;
import io.restassured.response.Response;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI =  "http://localhost:8080";
        RestAssured.port = 8080;
    }

    /**
     * test_createGroup
     */
    @Test
    public void a() {
        final AddGroupRequest addGroupRequest = new AddGroupRequest();
        addGroupRequest.setName("Nirma");
        final Response response =
                given().accept("application/json")
                        .contentType("application/json")
                        .body(addGroupRequest)
                        .when()
                        .post("/users/addgroup")
                        .then().statusCode(200)
                        .extract()
                        .response();

    }

    /**
     * test_addUserToGroup
     */
    @Test
    public void b() {
        final UserGroupRequest userGroupRequest = new UserGroupRequest();
        userGroupRequest.setGroupName("Nirma");
        userGroupRequest.setUserName("Hema");
        final Response response = given()
                .accept("application/json")
                .contentType("application/json")
                .body(userGroupRequest)
                .when()
                .post("/users/addusertogroup")
                .then()
                .statusCode(200)
                .extract()
                .response();

        final UserGroupRequest userGroupRequest2 = new UserGroupRequest();
        userGroupRequest.setGroupName("Nirma");
        userGroupRequest.setUserName("Rekha");
        final Response response2 = given()
                .accept("application/json")
                .contentType("application/json")
                .body(userGroupRequest)
                .when()
                .post("/users/addusertogroup")
                .then()
                .statusCode(200)
                .extract()
                .response();

    }
}