package com.nikhil.api.filters;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class ApiLoggingFilter {

    public static RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }

    public static ResponseLoggingFilter responseLoggingFilter() {
        return new ResponseLoggingFilter();
    }

}
