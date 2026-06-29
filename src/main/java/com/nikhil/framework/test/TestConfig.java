package com.nikhil.framework.test;

import com.nikhil.framework.config.ConfigReader;

public class TestConfig {
    public static void main(String[] args) {

        ConfigReader config = ConfigReader.getInstance();

        System.out.println(config.getBrowser());

    }
}
