package com.booking.stepdefinitions;
import com.api.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.support.SupportLibrary;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class createBooking {
    TestContext context;


    public createBooking(TestContext context){
        this.context=context;
    }
    Response res;


    @When("I make a reservation with {}, {}, {}, {}, {}, {}")
    public void i_make_a_reservation_inserting_all_the_information(String roomid, String lastname, String firstname,Boolean depositpaid, String checkin, String checkout) throws JsonProcessingException {
        String tokenValue = context.getToken();
        String InfoClientJSONformat = "";
        InfoClientJSONformat = SupportLibrary.convertJavaObjectToJson(ClientInformation.getAllClientInfo(roomid, lastname, firstname,depositpaid, checkin, checkout));
        System.out.println("############# "+InfoClientJSONformat);
        res = given()
                .log().all()
                .accept("*/*")
                .contentType(ContentType.JSON)
                .cookie("token", tokenValue)
                .body(InfoClientJSONformat)
                .post(URL.post_url_to_create_reservation);
        System.out.println(res.getBody().asString());
        res.then()
                .statusCode(200);
    }
    @Then("I receive the reservation and the process ends successfully")
    public void I_receive_the_reservation_and_the_process_ends_successfully() {

        String tokenValue = context.getToken();
        System.out.println("Token created __>> "+tokenValue);
        res= given()
                .queryParam("roomid",1000)
                .contentType(ContentType.JSON)
                .cookie("token", tokenValue)
                .get(URL.get_url_to_retrive_booking_room);
        System.out.println(res.getBody().asString());
        res.then()
                .statusCode(200);


        context.bookingID = res.jsonPath().get("bookings[0].bookingid");

        System.out.println(" BOOKING ID:    --> " + context.bookingID);
        String name = res.jsonPath().get("bookings[0].firstname").toString();
        Assert.assertEquals("Daniele", name);
        String lastname = res.jsonPath().get("bookings[0].lastname").toString();
        Assert.assertEquals("Marino", lastname);

    }



}
