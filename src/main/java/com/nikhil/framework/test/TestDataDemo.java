package com.nikhil.framework.test;

import com.nikhil.framework.data.TestDataManager;

public class TestDataDemo {

    public static void main(String[] args) {

        System.out.println(
                TestDataManager.getValue(
                        "registration.properties",
                        "firstname"));

        System.out.println(
                TestDataManager.getValue(
                        "registration.properties",
                        "lastname"));

        System.out.println(
                TestDataManager.getValue(
                        "registration.properties",
                        "email"));

        System.out.println(
                TestDataManager.getValue(
                        "registration.properties",
                        "city"));

    }

}