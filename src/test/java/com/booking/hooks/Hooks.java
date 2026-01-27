package com.booking.hooks;

import io.restassured.RestAssured;
import io.cucumber.java.Before;

public class Hooks {
    @Before
    public void setup() {
        //String baseUrl = System.getenv("BOOKING_API_URL");
        RestAssured.baseURI = "https://automationintesting.online/api";
    }
}
