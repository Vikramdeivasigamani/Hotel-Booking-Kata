package com.booking.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingSteps {

    private Response response;
    private int bookingId;

    @When("I create a new booking with the following details:")
    public void iCreateANewBookingWithTheFollowingDetails(DataTable dataTable) {

        Map<String, String> bookingData = dataTable.asMaps().get(0);

        Map<String, Object> bookingDates = Map.of(
                "checkin", bookingData.get("checkIn"),
                "checkout", bookingData.get("checkOut")
        );

        Map<String, Object> requestBody = Map.of(
                "roomid", Integer.parseInt(bookingData.get("roomid")),
                "firstname", bookingData.get("firstname"),
                "lastname", bookingData.get("lastname"),
                "depositpaid", Boolean.parseBoolean(bookingData.get("depositpaid")),
                "bookingdates", bookingDates,
                "email", bookingData.get("email"),
                "phone", bookingData.get("phone")
        );
        response = given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", "Bearer " + AuthenticationSteps.token)
                .body(requestBody)
                .when()
                .post("/booking");
    }

    @Then("the booking is created successfully with a booking id")
    public void theBookingIsCreatedSuccessfullyWithABookingId() {
        response.then()
                .log().all()
                .statusCode(201) //Bug: In Swagger it states  200, but api returns 201
                .body("bookingid", notNullValue());

        bookingId = response.jsonPath().getInt("bookingid");
    }

    @Given("a booking exists")
    public void aBookingExists() {
        //System.out.println("!!!!Using token: " + AuthenticationSteps.token);
        response = given()
                .log().all()
                .contentType("application/json")
                .header("Cookie", "token=" + AuthenticationSteps.token)
                .when()
                .get("/booking/" + bookingId);

        response.then()
                .log().all()
                .statusCode(200)
                .body("firstname", notNullValue());
    }
    @When("I delete the booking")
    public void iDeleteTheBooking() {
        response = given()
                .log().all()
                .header("Cookie", "token=" + AuthenticationSteps.token)
                .when()
                .delete("/booking/" + bookingId);
    }
    @When("I delete the booking {int}")
    public void iDeleteTheBookingString(int id) {
        response = given()
                .log().all()
                .header("Cookie", "token=" + AuthenticationSteps.token)
                .when()
                .delete("/booking/" + id);
        bookingId = id;
    }

    @Then("the booking is removed")
    public void theBookingIsRemoved() {
        response.then()
                .statusCode(200)
                .log().all();

        response = given()
                .log().all()
                .header("Cookie", "token=" + AuthenticationSteps.token)
                .when()
                .get("/booking/" + bookingId);

        response.then()
                .log().all()
                .body("error", equalTo("Failed to fetch booking: 404"))
                .statusCode(404);

    }
}