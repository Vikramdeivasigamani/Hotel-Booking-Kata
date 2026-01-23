package com.booking.impl;

import com.booking.models.ConfigReader;
import com.booking.models.Constants;
import com.booking.models.CreateBookingRequest;
import com.booking.utils.ApiUtils;
import com.booking.utils.JsonUtil;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class BookingServiceImpl {
    private final Map<String, String> headerMap;
    private final Map<String, String> cookieMap;

    public BookingServiceImpl(String accessToken) {
        headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        cookieMap = new HashMap<>();
        cookieMap.put("token", accessToken);
    }


    public Response postCreateBooking(CreateBookingRequest createBookingRequest) {
        // In case if any additional Header we want to add specific to this API
        HashMap<String, String> headers = new HashMap<>(headerMap);
        String payload = JsonUtil.toJson(createBookingRequest);
        Response response = ApiUtils.post(ConfigReader.BASE_URL, Constants.CREATE_BOOKING, headerMap, payload);
        return response;
    }

    public Response postCreateBooking(CreateBookingRequest createBookingRequest, String url) {
        String payload = JsonUtil.toJson(createBookingRequest);
        Response response = ApiUtils.post(ConfigReader.BASE_URL, url, headerMap, payload);
        return response;
    }

    public Response putCreateBooking(CreateBookingRequest createBookingRequest) {
        ;
        String payload = JsonUtil.toJson(createBookingRequest);
        Response response = ApiUtils.put(ConfigReader.BASE_URL, Constants.CREATE_BOOKING, headerMap, cookieMap, payload);
        return response;
    }

    public Response getBookingDetails(Map<String, Integer> params) {
        Response response = ApiUtils.get(ConfigReader.BASE_URL, Constants.CREATE_BOOKING, params, cookieMap, headerMap);
        return response;
    }

    public Response deleteBookingDetails(int id) {
        Response response = ApiUtils.delete(ConfigReader.BASE_URL, Constants.CREATE_BOOKING + "/" + id, cookieMap, headerMap);
        return response;
    }


}
