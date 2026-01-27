package com.booking.stepdefinitions;
import com.api.URL;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class deleteBooking {

    TestContext context;

    public deleteBooking(TestContext context){
        this.context=context;
    }
    Response res;


    @When("I make a request to delete it")
    public void i_make_a_request_to_delete_it() {
        String tokenValue = context.getToken();
        res = given()
                .log().all()
                .pathParam("bookingid",context.bookingID)
                .cookie("token", tokenValue)
                .delete(URL.delete_booking_reservation);

    }
    @Then("The reservation should be successfully deleted")
    public void the_reservation_should_be_successfully_deleted() {
        res.then().statusCode(200);
    }
}
