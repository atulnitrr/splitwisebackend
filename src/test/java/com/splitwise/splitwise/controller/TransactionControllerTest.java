package com.splitwise.splitwise.controller;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.splitwise.splitwise.model.request.TransDetail;
import com.splitwise.splitwise.model.request.TransactionRequest;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionControllerTest {

    @Autowired
    TransactionController transactionController;

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8890;
    }

    @Test
    public void test() {
        final TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setGroupName("Nirma");
//        new TransDetail("Rekha", 60)
        transactionRequest.setTransDetail(Arrays.asList( new TransDetail("Hema", 50), new TransDetail("Rekha", 60) ));
        final Response response =
                given()
                        .contentType("application/json")
                        .accept("application/json")
                        .body(transactionRequest)
                        .when()
                        .post("/trans")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    @Test
    public void test_3() {
        final TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setGroupName("Warrior");
        transactionRequest.setTransDetail(Arrays.asList( new TransDetail("Rekha", 80), new TransDetail("Roger", 30) ));
        final Response response =
                given()
                        .contentType("application/json")
                        .accept("application/json")
                        .body(transactionRequest)
                        .when()
                        .post("/trans")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    @Test
    public void test_GetUserBalanceInALlGroup() {

        final Response response =
                given()
                        .accept("application/json")
                        .when()
                        .get("/trans/balance/user/Roger")
                        .then()
                        .statusCode(200).extract()
                        .response();
        System.out.println(response.asString());
    }

    @Test
    public void test_GetByUserGroup() {

        final Response response = given()
                .accept("application/json")
                .when()
                .get("/trans/Nirma/Rekha")
                .then()
                .statusCode(200)
                .extract()
                .response();
        System.out.println(response.asString());
    }


    @Test
    public void test_GetUserBalanceINGroup() {

        final Response response = given()
                .accept("application/json")
                .when()
                .get("/trans/Nirma")
                .then()
                .statusCode(200)
                .extract()
                .response();
        System.out.println(response.asString());
    }
}