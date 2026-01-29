package com.booking.hooks;

import com.booking.stepdefinitions.AuthenticationSteps;
import com.booking.stepdefinitions.ScenarioContext;
import io.cucumber.java.After;
import io.restassured.RestAssured;
import io.cucumber.java.Before;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Hooks {

    private final ScenarioContext context;
    public Hooks(ScenarioContext context) {
        this.context = context;
    }

    @Before
    public void setup() {
        //String baseUrl = System.getenv("BOOKING_API_URL");
        RestAssured.baseURI = "https://automationintesting.online/api";
    }

    @After
    public void cleanup() {
        if (context.bookingIds != null) {
            System.out.println("---- Cleaning up created bookings ----");

            for (Integer id : context.bookingIds) {
                try {
                    Response response = given()
                            .header("Cookie", "token=" + context.token)
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
            context.bookingIds.clear();
        }
    }
}
