package com.booking.hooks;

import com.booking.stepdefinitions.AuthenticationSteps;
import com.booking.stepdefinitions.BaseTest;
import io.cucumber.java.After;
import io.restassured.RestAssured;
import io.cucumber.java.Before;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Hooks {
    @Before
    public void setup() {
        //String baseUrl = System.getenv("BOOKING_API_URL");
        RestAssured.baseURI = "https://automationintesting.online/api";
    }

    @After
    public void cleanup() {
        if (BaseTest.bookingIds != null) {
            System.out.println("---- Cleaning up created bookings ----");

            for (Integer id : BaseTest.bookingIds) {
                try {
                    Response response = given()
                            .header("Cookie", "token=" + AuthenticationSteps.token)
                            .delete("/booking/" + id);

                    if (response.getStatusCode() == 200) {
                        System.out.println("Succesfully deleted booking id: " + id);
                    }
                    else {
                        System.out.println("Failed to delete booking id: " + id);
                    }
                } catch (Exception e) {
                    System.out.println("Error cleaning up booking id: " + id + " -> " + e.getMessage());
                }
            }
            BaseTest.bookingIds.clear();
        }
    }
}
