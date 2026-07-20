package com.nikhil.api.tests;

import com.nikhil.api.service.AuthService;
import com.nikhil.api.service.BookingService;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.nikhil.api.specs.ResponseSpecBuilderUtil.getSuccessResponseSpec;
import static org.hamcrest.Matchers.equalTo;

public class BookingCrudTest {

    private String token;
    private int bookingId;

    private AuthService authService;
    private BookingService bookingService;


    @BeforeClass
    public void setup() {

        authService = new AuthService();
        bookingService = new BookingService();

        token = authService.getToken();
        bookingId = bookingService.createBooking();
    }

    @Test
    public void updateBooking() {

        Response response = bookingService.updateBooking(token, bookingId);

        response.then()
                .spec(getSuccessResponseSpec())
                .body("lastname", equalTo("PUpdated"))
                .body("additionalneeds", equalTo("BreakfastUpdated"));
    }

    @Test(dependsOnMethods = "updateBooking")
    public void deleteBooking() {

        Response response = bookingService.deleteBooking(token, bookingId);

        response.then().statusCode(201);

    }

}