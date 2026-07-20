package com.nikhil.api.specs;

import com.nikhil.api.filters.ApiLoggingFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecBuilderUtil {

    public static RequestSpecification getRequestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                //.setAccept(ContentType.JSON)
                .addFilter(ApiLoggingFilter.requestLoggingFilter())
                .addFilter(ApiLoggingFilter.responseLoggingFilter())
                .build();


    }
}