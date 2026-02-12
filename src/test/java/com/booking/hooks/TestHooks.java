package com.booking.hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class TestHooks {

    @Before(order = 0)
    public void setup() {
        RestAssured.baseURI = "https://automationintesting.online/api";

        int statusCode = RestAssured
                .given()
                .log().all()
                .when()
                .get("/booking/actuator/health")
                .then()
                .log().all()
                .extract()
                .statusCode();

        if (statusCode != 200) {
            throw new RuntimeException("API is not available");
        }
    }
}
