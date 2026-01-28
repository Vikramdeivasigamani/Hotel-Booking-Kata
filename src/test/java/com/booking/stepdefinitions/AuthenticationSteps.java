package com.booking.stepdefinitions;

import java.util.Map;

import com.booking.api.BookingApi;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthenticationSteps {
    private String username;
    private String password;
    private final ScenarioContext context;
    private final BookingApi bookingApi;
    public AuthenticationSteps(ScenarioContext context) {
        this.context = context;
        this.bookingApi = new BookingApi();
    }

    @Given("I have a username {string} and password {string}")
    public void iHaveUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @When("I send a login request")
    public void iSendALoginRequest() {
        context.response = bookingApi.login(username, password);
    }

    @Then("I should receive an authentication token")
    public void iShouldReceiveAnAuthenticationToken() {
        context.response.then()
                .log().all()
                .statusCode(200)
                .body("token", not(emptyOrNullString()));

        context.token = context.response.jsonPath().getString("token");
    }

    @Then("I should receive a statuscode {int} with message {string}")
    public void iShouldReceiveAStatuscodeWithMessage(int statusCode, String errorMessage) {
        context.response.then()
                .log().all()
                .statusCode(statusCode)
                .body("error", equalTo(errorMessage));
    }
    @Then("I should receive a statuscode {int}")
    public void iShouldReceiveAStatuscode(int statusCode) {
        context.response.then()
                .log().all()
                .statusCode(statusCode);
    }
    @Then("the token should not be present in the response")
    public void theTokenShouldNotBePresentInTheResponse() {
        context.response.then()
                .log().all()
                .body("token", nullValue())
                .statusCode(401);

    }
}