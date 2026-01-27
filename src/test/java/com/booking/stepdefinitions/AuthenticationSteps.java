package com.booking.stepdefinitions;

import java.util.Map;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthenticationSteps {
    private Response response;
    private String username;
    private String password;
    private String token;

    @Given("I have a username {string} and password {string}")
    public void iHaveUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @When("I send a login request")
    public void iSendALoginRequest() {
        response = given()
                .log().all()
                .contentType("application/json")
                .body(Map.of(
                        "username", username,
                        "password", password
                ))
                .when()
                .post("/auth/login");
    }

    @Then("I should receive an authentication token")
    public void iShouldReceiveAnAuthenticationToken() {
        response.then()
                .statusCode(200)
                .body("token", not(emptyOrNullString()))
                .log().all();

        //token = response.jsonPath().getString("token");
    }

    @Then("I should receive an error message {string}")
    public void iShouldReceiveAnErrorMessage(String errorMessage) {
        response.then()
                .statusCode(401)
                .body("error", equalTo(errorMessage))
                .log().all();
    }

    @Then("the token should not be present in the response")
    public void theTokenShouldNotBePresentInTheResponse() {
        response.then()
                .body("token", nullValue())
                .statusCode(401)
                .log().all();
    }
}