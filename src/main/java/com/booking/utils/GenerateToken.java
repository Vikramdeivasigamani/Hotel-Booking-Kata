package com.booking.utils;

import io.restassured.response.Response;
import com.booking.models.ConfigReader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GenerateToken {
    public static String generateAccessToken() {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("username", ConfigReader.USERNAME);
        requestParams.put("password", ConfigReader.PASSWORD);
        String credentials = Base64.getEncoder().encodeToString((ConfigReader.USERNAME + ":" + ConfigReader.PASSWORD).getBytes(StandardCharsets.UTF_8)
        );

        Response token = given().log().all().header("Authorization",credentials).body(requestParams)
                .when().log().all().post(ConfigReader.BASE_URL + "/auth/login")
                .then().log().all().extract().response();

        return token.jsonPath().getString("token");
    }
}
