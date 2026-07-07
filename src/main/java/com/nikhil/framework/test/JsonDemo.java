package com.nikhil.framework.test;

import com.nikhil.framework.constants.FrameworkConstants;
import com.nikhil.framework.data.JsonReader;
import com.nikhil.framework.data.TestDataManager;

public class JsonDemo {

    public static void main(String[] args) {

        System.out.println(
                TestDataManager.getValue(
                        "registration.json",
                        "firstname"));

        System.out.println(
                TestDataManager.getValue(
                        "registration.json",
                        "lastname"));

        System.out.println(
                TestDataManager.getValue(
                        "registration.json",
                        "email"));

        System.out.println(
                TestDataManager.getValue(
                        "registration.json",
                        "city"));

    }

}