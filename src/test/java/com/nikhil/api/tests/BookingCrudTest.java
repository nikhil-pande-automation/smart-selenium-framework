package com.nikhil.api.tests;

import com.nikhil.api.pojo.Booking;
import com.nikhil.api.pojo.BookingDates;
import com.nikhil.api.pojo.CreateBookingResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.nikhil.api.specs.RequestSpecBuilderUtil.getRequestSpec;
import static org.hamcrest.Matchers.equalTo;
import static com.nikhil.api.specs.ResponseSpecBuilderUtil.getSuccessResponseSpec;

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
                .spec(getRequestSpec())
                .cookie("token", token)
                .body(reqBody)
                .when()
                .put("/booking/" + bookingId);

        response.then()
                .spec(getSuccessResponseSpec())
                .body("lastname", equalTo("PUpdated"))
                .body("additionalneeds", equalTo("BreakfastUpdated"));
        System.out.println("Booking Updated Successfully");
    }

    @Test(dependsOnMethods = "updateBooking")
    public void deleteBooking() {
        Response response = RestAssured
                .given()
                .spec(getRequestSpec())
                .cookie("token", token)
                .when()
                .delete("/booking/" + bookingId);

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
                .spec(getRequestSpec())
                .body(reqBody)
                .when()
                .post("/auth");

        response.then().spec(getSuccessResponseSpec());
        token = response.jsonPath().getString("token");
        System.out.println("Token : " + token);
    }

    private void createBooking() {

        //Serialization: JSON to POJO conversion
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2026-07-20");
        bookingDates.setCheckout("2026-07-25");
        Booking booking = new Booking();
        booking.setFirstname("Nik");
        booking.setLastname("P");
        booking.setTotalprice(5000);
        booking.setDepositpaid(true);
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");

        Response response = RestAssured
                .given()
                .spec(getRequestSpec())
                .body(booking)
                .when()
                .post("/booking");

        response.then().spec(getSuccessResponseSpec());
        // Deserialization: JSON -> POJO using Jackson. means JSON to CreateBookingResponse Object conversion
        /**
         * How do you deserialize a response in Rest Assured?
         *
         * We create POJO classes matching the response JSON structure and use response.as(MyPojo.class).
         * Rest Assured uses Jackson internally to convert the JSON response into a Java object,
         * after which we access values through getters instead of JSONPath.
         */
        CreateBookingResponse bookingResponse = response.as(CreateBookingResponse.class);
        bookingId = bookingResponse.getBookingid();
        System.out.println("Booking Id : " + bookingId);
        System.out.println(bookingResponse.getBooking().getFirstname());
        System.out.println(bookingResponse.getBooking().getLastname());
    }
}