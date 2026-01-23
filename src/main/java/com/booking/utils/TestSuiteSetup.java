package com.booking.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public abstract class TestSuiteSetup{

    public static String accessToken;

    @BeforeClass
    public void suiteSetup() {
        accessToken = GenerateToken.generateAccessToken();
        if (RestAssured.filters().isEmpty()) {
            RestAssured.filters(new AllureRestAssured());
        }
    }
}
