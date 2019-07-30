package com.splitwise.splitwise.controller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import java.util.Arrays;
import com.splitwise.splitwise.model.request.AddGroupRequest;
import com.splitwise.splitwise.model.request.RegisterUserRequest;
import com.splitwise.splitwise.model.request.UserGroupRequest;
import io.restassured.response.Response;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;


    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI =  "http://localhost";
        RestAssured.port = 8890;
    }


    /**
     * Warrior, Rekha, Roger
     */
    /**
     * test_createGroup
     */
//    @Test
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
        userGroupRequest.setUserNames(Arrays.asList("Hema", "Rekha"));
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

    }


    @Test
    public void c() {
        final AddGroupRequest addGroupRequest = new AddGroupRequest();
        addGroupRequest.setName("Warrior");
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
    public void d() {
        final UserGroupRequest userGroupRequest = new UserGroupRequest();
        userGroupRequest.setGroupName("Warrior");
        userGroupRequest.setUserNames(Arrays.asList("Rekha", "Roger"));
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
        System.out.println(response.asString());

    }

}