package com.booking.stepdefinitions;

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

public class BaseTest {
    public static List<Integer> bookingIds = new ArrayList<>();
    public Response response;
}