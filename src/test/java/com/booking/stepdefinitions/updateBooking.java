package com.booking.stepdefinitions;
import com.api.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.support.SupportLibrary;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class updateBooking {

    TestContext context;

    public updateBooking(TestContext context){
        this.context=context;
    }
    Response res;

    @Given("I have the bookingid for the roomid <{int}>")
    public void iHaveTheBookingidForTheRoomid(int valueroomid) {

        String tokenValue = context.getToken();
        System.out.println("Token created __>> "+tokenValue);
        res= given()
                .queryParam("roomid",valueroomid)
                .contentType(ContentType.JSON)
                .cookie("token", tokenValue)
                .get(URL.get_url_to_retrive_booking_room);
        System.out.println(res.getBody().asString());
        res.then()
                .statusCode(200);

        context.bookingID = res.jsonPath().get("bookings[0].bookingid");

    }


    @When("I make a request to update first name and Last name")
    public void user_makes_a_request_to_update_first_name_and_last_name() throws JsonProcessingException {
        String updateInfoClientJSONformat = "";
        String tokenValue = context.getToken();
        updateInfoClientJSONformat = SupportLibrary.convertJavaObjectToJson(ClientInformation.updateClientNameAndLastName());
        res = given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept("*/*")
                .pathParam("bookingid",context.bookingID)
                .cookie("token", tokenValue)
                .body(updateInfoClientJSONformat)
                .put(URL.put_url_to_update_booking_reservation+"/{bookingid}");
                //.put("https://automationintesting.online/api/booking/4");
        System.out.println(res.getBody().asString());

        res.then()
                .statusCode(200);
    }
    @Then("I should get the success status code")
    public void user_should_get_the_success_status_code() {

        String tokenValue = context.getToken();
        System.out.println("Token created after update__>> "+tokenValue);
        res= given()
                .queryParam("roomid",1000)
                .contentType(ContentType.JSON)
                .cookie("token", tokenValue)
                .get(URL.get_url_to_retrive_booking_room);

        System.out.println(res.getBody().asString());
        res.then()
                .statusCode(200);
        String name = res.jsonPath().get("bookings[0].firstname").toString();
        Assert.assertEquals("Jimmy", name);
        System.out.println(name);
        String lastname = res.jsonPath().get("bookings[0].lastname").toString();
        Assert.assertEquals("Love",lastname);
        System.out.println(lastname);
    }


}
