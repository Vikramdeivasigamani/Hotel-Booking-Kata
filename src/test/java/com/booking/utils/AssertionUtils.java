package com.booking.utils;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssertionUtils {

    // ----------------------
    // Positive scenario: check response matches expected payload
    // ----------------------
    public static void verifyResponseMatchesPayload(Response response, Map<String, String> expected) {
        Map<String, Object> actual = response.jsonPath().getMap(""); // Root response map

        List<String> errors = new ArrayList<>();
        System.out.println("==== Verifying response for scenario ====");

        // Check top-level keys
        for (String key : expected.keySet()) {
            try {
                if ("checkin".equals(key) || "checkout".equals(key)) {
                    // Handle bookingdates separately
                    Map<String, String> bookingDates = response.jsonPath().getMap("bookingdates", String.class, String.class);
                    if (bookingDates.containsKey(key)) {
                        assertEquals(expected.get(key), bookingDates.get(key), "Mismatch for " + key);
                    } else {
                        errors.add("Expected key '" + key + "' is missing in bookingdates!");
                    }
                } else {
                    if (actual.containsKey(key)) {
                        assertEquals(expected.get(key), String.valueOf(actual.get(key)), "Mismatch for key: " + key);
                    } else {
                        errors.add("Expected key '" + key + "' is missing in response!");
                    }
                }
            } catch (AssertionError e) {
                errors.add(e.getMessage());
            }
        }

        // If there are any errors, fail the test
        if (!errors.isEmpty()) {
            System.out.println("==== Response Verification Errors ====");
            errors.forEach(System.out::println);
            fail("Response validation failed! See errors above.");
        }
    }


    // ----------------------
    // Negative scenario: check validation error response
    // ----------------------
    public static void verifyValidationErrors(Response response, List<String> expectedErrors) {
        Assertions.assertEquals(400, response.getStatusCode(), "Expected 400 Bad Request");

        List<String> actualErrors = response.jsonPath().getList("errors");
        Assertions.assertNotNull(actualErrors, "Errors array should be present");

        StringBuilder missingErrors = new StringBuilder();
        for (String expected : expectedErrors) {
            if (!actualErrors.contains(expected)) {
                missingErrors.append("Expected error message not found: ").append(expected).append("\n");
            }
        }

        if (missingErrors.length() > 0) {
            Assertions.fail("Validation error check failed! See errors above.\n" + missingErrors);
        }
    }
}
