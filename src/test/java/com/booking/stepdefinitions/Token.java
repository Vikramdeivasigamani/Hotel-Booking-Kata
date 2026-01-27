package com.booking.stepdefinitions;

import com.api.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.payloads.Credentials;
import com.support.SupportLibrary;
import io.cucumber.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import static com.api.access.getCredentials;
import static io.restassured.RestAssured.given;

public class Token {

    private TestContext context;
    Credentials payload = new Credentials();
    private Response res;
    String token_created;
    public Token(TestContext context){
        this.context = context;
    }

    @Given("I have a valid token")
    public String validToken() throws JsonProcessingException {

        payload.setUsername(getCredentials().getString("username"));
        payload.setPassword(getCredentials().getString("password"));
        // convert
        String TokenJSONformat = "";
        TokenJSONformat = SupportLibrary.convertJavaObjectToJson(payload);
        System.out.println();  //sout
        System.out.println("Token:  " +TokenJSONformat);

        res = given().contentType(ContentType.JSON)
                .accept("*/*")
                .body(TokenJSONformat)
                .log().all()
                .post(URL.post_url_to_create_token);

        token_created = res.jsonPath().get("token").toString();
        context.setToken(token_created);
        System.out.println("inside Token class:  "+token_created);
       return token_created;

    }
}
