package com.nikhil.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

public class BookingCrudTest {

    private String token;
    private int bookingId;

    @BeforeClass
    public void setup() {
        createToken();
        createBooking();
    }

    @Test
    public void updateBooking() {

        String reqBody = """
                {
                 "firstname":"Nik",
                "lastname":"PUpdated",
                "totalprice":10000,
                "depositpaid":true,
                "bookingdates":{
                    "checkin":"2026-07-20",
                    "checkout":"2026-07-25"
                    },
                    "additionalneeds":"BreakfastUpdated"
                }
                """;

        Response response = RestAssured
                .given()
                .contentType("application/json")
                .accept("application/json")
                .cookie("token", token)
                .body(reqBody)
                .when()
                .put("https://restful-booker.herokuapp.com/booking/" + bookingId);

        response.then()
                .statusCode(200)
                .body("lastname", equalTo("PUpdated"))
                .body("additionalneeds", equalTo("BreakfastUpdated"));
        System.out.println("Booking Updated Successfully");
    }

    @Test(dependsOnMethods = "updateBooking")
    public void deleteBooking() {
        Response response = RestAssured
                .given()
                .cookie("token", token)
                .when()
                .delete("https://restful-booker.herokuapp.com/booking/" + bookingId);

        response.then().statusCode(201);

        System.out.println("Booking Deleted Successfully");

    }

    private void createToken() {
        String reqBody = """
                {
                "username":"admin",
                "password":"password123"
                }
                """;

        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body(reqBody)
                .when()
                .post("https://restful-booker.herokuapp.com/auth");

        response.then().statusCode(200);
        token = response.jsonPath().getString("token");
        System.out.println("Token : " + token);
    }

    private void createBooking() {
        String reqBody = """
                {
                "firstname":"Nik",
                "lastname":"P",
                "totalprice":5000,
                "depositpaid":true,
                "bookingdates":{
                    "checkin":"2026-07-20",
                    "checkout":"2026-07-25"
                    },
                    "additionalneeds":"Breakfast"
                }
                """;

        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body(reqBody)
                .when()
                .post("https://restful-booker.herokuapp.com/booking");
        response.then().statusCode(200);
        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Booking Id : " + bookingId);
    }
}