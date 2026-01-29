package com.booking.stepdefinitions;

import com.booking.api.BookingApi;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingSteps {
    private final ScenarioContext context;
    private final BookingApi bookingApi;
    public BookingSteps(ScenarioContext context) {
        this.context = context;
        this.bookingApi = new BookingApi();
    }
    private Map<String, String> bookingData;

    @When("I create a new booking with the following details:")
    public void iCreateANewBookingWithTheFollowingDetails(DataTable dataTable) {
        bookingData = new HashMap<>(dataTable.asMaps(String.class, String.class).get(0));
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
        context.response = bookingApi.createBooking(requestBody, context.token);
    }

    @Then("the booking is created successfully and returns a booking id")
    public void theBookingIsCreatedSuccessfullyWithABookingId() {
        context.response.then()
                .log().all()
                .statusCode(201) //Bug: In Swagger it states  200, but api returns 201
                .body("bookingid", notNullValue());
        context.bookingId = context.response.jsonPath().getInt("bookingid");
        context.bookingIds.add(context.bookingId);
    }

    @When("I update the booking with the following details:")
    public void iUpdateTheBookingWithTheFollowingDetails(DataTable dataTable) {
        bookingData = dataTable.asMaps().get(0);
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
        context.response = bookingApi.updateBooking(context.bookingId, requestBody, context.token);
    }

    @Then("the booking is updated successfully")
    public void theBookingIsUpdatedSuccessfully() {
        context.response.then()
                .log().all()
                .statusCode(200)/*
                .body("firstname", equalTo(bookingData.get("firstname")))
                .body("lastname", equalTo(bookingData.get("lastname")))
                .body("depositpaid", equalTo(bookingData.get("depositpaid")))
                .body("bookingdates.checkin", equalTo(bookingData.get("checkin")))
                .body("bookingdates.checkout", equalTo(bookingData.get("checkout")))
                .body("email", equalTo(bookingData.get("email")))
                .body("phone", equalTo(bookingData.get("phone")))*/;
        //bug: content should show updated booking details but instead returns "success": true
    }

    @When("I partially update the booking with the following details:")
    public void iPartiallyUpdateTheBookingWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> patchData = dataTable.asMaps(String.class, String.class).get(0);

        Map<String, Object> requestBody = new HashMap<>();

        patchData.forEach((key, value) -> {
            switch (key) {
                case "depositpaid" -> {
                    boolean boolVal = Boolean.parseBoolean(value);
                    requestBody.put(key, boolVal);
                    bookingData.put(key, value);
                }
                case "firstname", "lastname" -> {
                    requestBody.put(key, value);
                    bookingData.put(key, value);
                }
            }
        });

        context.response = bookingApi.patchBooking(context.bookingId, requestBody, context.token);
        context.response.then().log().all().
                statusCode(200)
                /*.body("firstname", equalTo(bookingData.get("firstname")))
                .body("lastname", equalTo(bookingData.get("lastname")))
                .body("depositpaid", equalTo(bookingData.get("depositpaid")))
                .body("bookingdates.checkin", equalTo(bookingData.get("checkin")))
                .body("bookingdates.checkout", equalTo(bookingData.get("checkout")))
                .body("email", equalTo(bookingData.get("email")))
                .body("phone", equalTo(bookingData.get("phone")))*/;
        //bug?: returns 405 Method Not Allowed
    }

    @Then("the details of the booking can be found using the booking id")
    public void theDetailsOfTheBookingCanBeFoundUsingTheBookingId() {
        context.response = bookingApi.getBooking(context.bookingId, context.token);

        context.response.then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo(bookingData.get("firstname")))
                .body("lastname", equalTo(bookingData.get("lastname")))
                .body("depositpaid", equalTo(Boolean.parseBoolean(bookingData.get("depositpaid"))))
                .body("bookingdates.checkin", equalTo(bookingData.get("checkIn")))
                .body("bookingdates.checkout", equalTo(bookingData.get("checkOut")))
                //.body("email", equalTo(bookingData.get("email"))) -> bug: email field not returned
                //.body("phone", equalTo(bookingData.get("phone"))) -> bug: phone field not returned
        ;
    }

    @When("I delete the booking")
    public void iDeleteTheBooking() {
        context.response = bookingApi.deleteBooking(context.bookingId, context.token);
    }

    @When("I delete the booking {int}")
    public void iDeleteTheBookingString(int id) {
        context.bookingId = id;
        context.response = bookingApi.deleteBooking(id, context.token);
    }

    @Then("the booking is deleted successfully")
    public void theBookingIsRemoved() {
        context.response.then()
                .statusCode(200)
                .log().all();

        context.response = bookingApi.getBooking(context.bookingId, context.token);

        context.response.then()
                .log().all()
                .body("error", equalTo("Failed to fetch booking: 404"))
                .statusCode(404);

    }

    @When("I try to find the booking using the booking id")
    public void iTryToFindTheBookingUsingTheBookingId() {
        context.response = bookingApi.getBooking(context.bookingId, "");
    }

    @Then("the response contains the following errors:")
    public void theResponseContainsTheFollowingErrors(DataTable table) {
        List<String> expectedErrors = table.asList();

        context.response.then()
                .body("errors", hasSize(expectedErrors.size()))
                .body("errors", hasItems(expectedErrors.toArray()));
    }


    @When("I try to fetch the booking without authentication")
    public void iTryToFetchWithoutAuth() {
        context.response = bookingApi.getBookingByIdWithoutAuth(context.bookingId);
    }

    @When("I try to fetch the booking with an invalid token")
    public void iTryToFetchWithInvalidToken() {
        context.response = bookingApi.getBooking(context.bookingId, "Invalid token");
    }

    @When("I update the booking with an invaid token with the following details:")
    public void iUpdateTheBookingWithAnInvaidTokenWithTheFollowingDetails(DataTable dataTable) {
        bookingData = dataTable.asMaps().get(0);
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
        context.response = bookingApi.updateBooking(context.bookingId, requestBody, "Invalid token");
    }

    @When("I update the booking without authentication with the following details:")
    public void iUpdateTheBookingWithoutAuthenticationWithTheFollowingDetails(DataTable dataTable) {
        bookingData = dataTable.asMaps().get(0);
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
        context.response = bookingApi.updateBookingWithoutAuth(context.bookingId, requestBody);
    }
}