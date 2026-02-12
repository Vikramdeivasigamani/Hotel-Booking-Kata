package com.booking.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.utils.AssertionUtils;

public class BookingSteps {

    private RequestSpecification request;
    private Response response;
    private Map<String, String> bookingPayload;

    @Given("a valid booking payload")
    public void givenValidBookingPayload(DataTable dataTable) {
        bookingPayload = dataTable.asMaps(String.class, String.class).get(0);

        request = RestAssured.given()
                .baseUri(RestAssured.baseURI) // Ensure baseURI is set globally in Hooks
                .header("Content-Type", "application/json")
                .body(buildBookingJson(bookingPayload))
                .log().all();
    }

    @Given("a booking payload with empty value")
    public void givenBookingPayloadWithEmptyValue(DataTable dataTable) {
        // Get first row as map
        Map<String, String> originalMap = dataTable.asMaps(String.class, String.class).get(0);

        // Make a modifiable copy
        bookingPayload = new HashMap<>(originalMap);

        // Replace "[empty]" with empty string
        bookingPayload.replaceAll((key, value) -> {
            if (value != null && value.trim().equalsIgnoreCase("[empty]")) {
                return ""; // <-- empty string
            }
            return value;
        });
        
        request = RestAssured.given()
                .baseUri(RestAssured.baseURI)
                .header("Content-Type", "application/json")
                .body(buildBookingJson(bookingPayload)).log().all();
    }

    @When("I send a POST request to {string}")
    public void sendPostRequest(String endpoint) {
        response = request
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Then("the response should be successful")
    public void verifyResponseStatus() {
        int expectedStatus = 201;
        if (bookingPayload.containsKey("missing")) { // simple flag example for negative test
            expectedStatus = 400; // could be different depending on negative scenario
        }
        assert response != null;
        if (response.statusCode() != expectedStatus) {
            throw new AssertionError("Expected status " + expectedStatus + " but got " + response.statusCode());
        }
    }

    @Then("the response should match the booking payload")
    public void verifyResponseMatchesPayload() {
        AssertionUtils.verifyResponseMatchesPayload(response, bookingPayload);
    }

    @Then("the response should indicate a validation error")
    public void verifyValidationErrors() {
        List<String> expectedErrors = List.of("size must be between 3 and 18");
        AssertionUtils.verifyValidationErrors(response, expectedErrors);
    }

    private String buildBookingJson(Map<String, String> payload) {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"roomid\":").append(payload.getOrDefault("roomid", "0")).append(",");
        sb.append("\"firstname\":\"").append(payload.getOrDefault("firstname", "")).append("\",");
        sb.append("\"lastname\":\"").append(payload.getOrDefault("lastname", "")).append("\",");
        sb.append("\"depositpaid\":").append(payload.getOrDefault("depositpaid", "false")).append(",");
        sb.append("\"bookingdates\":{")
                .append("\"checkin\":\"").append(payload.getOrDefault("checkin", "")).append("\",")
                .append("\"checkout\":\"").append(payload.getOrDefault("checkout", "")).append("\"},");
        sb.append("\"email\":\"").append(payload.getOrDefault("email", "")).append("\",");
        sb.append("\"phone\":\"").append(payload.getOrDefault("phone", "")).append("\"}");
        return sb.toString();
    }
}
