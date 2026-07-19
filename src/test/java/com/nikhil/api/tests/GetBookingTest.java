package com.nikhil.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

public class GetBookingTest {

    @Test
    public void getAllBookings() {

        Response response = RestAssured
                .given()
                .when()
                .get("https://restful-booker.herokuapp.com/booking");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test
    public void getBookingById() {
        Response response = RestAssured
                .given()
                .when()
                .get("https://restful-booker.herokuapp.com/booking/1");

        response.then()
                .statusCode(200)
                .body("firstname", equalTo("Jim"));
        response.prettyPrint();
    }


}