package com.booking.utils;

import com.booking.models.CreateBookingRequest;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

public class Commons {

    public static void setAllureLifecycle(String testDesc) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.updateTestCase(testResult -> {testResult.setName(testDesc);testResult.setDescription("Scenario:"+testDesc);});
    }

    public static CreateBookingRequest getCreateBooking(Integer roomId, String firstName, String lastName, Boolean depositPaid, String email, String phone, String checkinDate, String checkoutDate) {
        CreateBookingRequest.BookingDates bookingDates = new CreateBookingRequest.BookingDates(checkinDate,checkoutDate);
        CreateBookingRequest createBookingRequest = new CreateBookingRequest(roomId,firstName,lastName,depositPaid,email,phone,bookingDates);
        return createBookingRequest;
    }

    @Attachment(value = "Execution Logs", type = "text/plain")
    public static String attachLogs(String logs) {
        return logs;
    }

    @Step("{0}")
    public static void logStep(String message) {
        System.out.println(message); // Optional: also prints to console
    }

    public static void logger(String message) {
        logStep(message);
        attachLogs(message);
    }
}
