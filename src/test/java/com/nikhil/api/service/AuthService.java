package com.nikhil.api.service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static com.nikhil.api.specs.RequestSpecBuilderUtil.getRequestSpec;
import static com.nikhil.api.specs.ResponseSpecBuilderUtil.getSuccessResponseSpec;

public class AuthService {

    public String getToken() {

        String reqBody = """
                {
                  "username":"admin",
                  "password":"password123"
                }
                """;

        Response response = RestAssured
                .given()
                .spec(getRequestSpec())
                .body(reqBody)
                .when()
                .post("/auth");

        response.then().spec(getSuccessResponseSpec());

        return response.jsonPath().getString("token");

    }

}
