package com.booking.stepdefinitions;

import com.api.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.payloads.Credentials;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;


public class GetBookings {

    TestContext context;

    public GetBookings(TestContext context){
        this.context=context;
    }

    Response res;


    @When("I want to read the bookings for a room")
    public void i_want_to_read_the_bookings_for_a_room() throws JsonProcessingException {
        String tokenValue = context.getToken();
        System.out.println("########### "+tokenValue);
        res= given()
                .queryParam("roomid",2)
                .cookie("token", tokenValue)
                .log().all()
                .get(URL.get_url_to_retrive_booking_room);

    }


    @Then("I should receive all existing bookings")
    public void i_should_receive_all_existing_bookings() {
        res.then().statusCode(200)
                .body("bookings[0].firstname", equalTo("Erica"))
                .log().all();

        String name = res.jsonPath().get("bookings[0].firstname").toString();
        Assert.assertEquals(name,"Erica");
        String lastname = res.jsonPath().get("bookings[0].lastname").toString();
        Assert.assertEquals(lastname,"Bowthorpe");

    }

}
