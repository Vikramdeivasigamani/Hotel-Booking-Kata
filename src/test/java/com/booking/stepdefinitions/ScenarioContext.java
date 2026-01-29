package com.booking.stepdefinitions;

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScenarioContext {
    public List<Integer> bookingIds = new ArrayList<>();
    public Response response;
    public String token;
    public Integer bookingId;
    public Map<String, String> bookingData;

}