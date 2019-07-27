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
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 8080;
    }

    @Test
    public void test() {
        final TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setGroupName("Nirma");
        transactionRequest.setTransDetail(Arrays.asList(new TransDetail("Hema", 10), new TransDetail("Rekha", 20)));
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
}