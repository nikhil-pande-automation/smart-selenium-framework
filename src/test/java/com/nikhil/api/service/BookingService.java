package com.nikhil.api.service;

import com.nikhil.api.pojo.Booking;
import com.nikhil.api.pojo.BookingDates;
import com.nikhil.api.pojo.CreateBookingResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static com.nikhil.api.specs.RequestSpecBuilderUtil.getRequestSpec;
import static com.nikhil.api.specs.ResponseSpecBuilderUtil.getSuccessResponseSpec;

public class BookingService {

    public int createBooking() {

        // Serialization: POJO -> JSON
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
        return bookingResponse.getBookingid();
    }


    public Response updateBooking(String token, int bookingId) {

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

        return RestAssured
                .given()
                .spec(getRequestSpec())
                .cookie("token", token)
                .body(reqBody)
                .when()
                .put("/booking/" + bookingId);
    }


    public Response deleteBooking(String token, int bookingId) {

        return RestAssured
                .given()
                .spec(getRequestSpec())
                .cookie("token", token)
                .when()
                .delete("/booking/" + bookingId);
    }


}
