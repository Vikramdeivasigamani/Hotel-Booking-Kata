package com.booking.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HealthCheckSteps {
    private Response response;

    @When("I check the health endpoint")
    public void iCheckTheHealthEndpoint() {
        response = given().log().all()
                .when()
                .get("/booking/actuator/health");
    }

    @Then("the API should respond with status UP")
    public void theAPIShouldRespondWithStatusUP() {
        response.then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo("UP"));

        }
    }
