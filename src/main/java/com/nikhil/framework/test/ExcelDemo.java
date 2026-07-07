package com.nikhil.framework.test;

import com.nikhil.framework.constants.FrameworkConstants;
import com.nikhil.framework.data.ExcelReader;
import com.nikhil.framework.data.TestDataManager;

public class ExcelDemo {

    public static void main(String[] args) {

        System.out.println(
                TestDataManager.getValue(
                        "registration.xlsx",
                        "firstname"));

        System.out.println(
                TestDataManager.getValue(
                        "registration.xlsx",
                        "lastname"));

        System.out.println(
                TestDataManager.getValue(
                        "registration.xlsx",
                        "email"));

        System.out.println(
                TestDataManager.getValue(
                        "registration.xlsx",
                        "city"));

    }

}