package com.booking.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;

public class JsonUtil {
    public static String toJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    public static void validateErrorResponse(Response response, List<String> expectedErrors) {
        List<String> actualErrors = response.jsonPath().getList("errors");
        Assert.assertNotNull(actualErrors, "Error message is null");
        Assert.assertEquals(new HashSet<>(actualErrors), new HashSet<>(expectedErrors));
    }
}