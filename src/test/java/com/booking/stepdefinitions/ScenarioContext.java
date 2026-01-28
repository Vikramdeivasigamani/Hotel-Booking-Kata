package com.booking.stepdefinitions;

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

public class ScenarioContext {
    public List<Integer> bookingIds = new ArrayList<>();
    public Response response;
    public String token;
    public Integer bookingId;
}