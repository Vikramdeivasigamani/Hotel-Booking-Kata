package com.booking.TesNGBooking;

import com.booking.impl.BookingServiceImpl;
import com.booking.models.GetBookingByRoomResponse;
import com.booking.utils.Commons;
import com.booking.utils.TestSuiteSetup;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;

public class DeleteBookingTest extends TestSuiteSetup {
    @Test(groups = {"sanity","regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.BLOCKER) @Feature("Delete Booking") @Owner("Sivakumari")
    public void deleteBooking() {
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        Commons.setAllureLifecycle("Delete a Existing Booking");
        try {
            int id = getBookingIdToDelete(bookingService);
            Response response = bookingService.deleteBookingDetails(id);
            Commons.logger("Response Status Code is :" + response.statusCode());
            Assert.assertEquals(response.statusCode(),200);
            Commons.logger("Response Data is :" + response.jsonPath().get());
            Assert.assertTrue(response.jsonPath().get("success").equals(true));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(groups = {"sanity","regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.BLOCKER) @Feature("Delete Booking") @Owner("Sivakumari")
    @Description("Delete a Booking that Does not exist")
    public void deleteInvalidBooking() {
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        Commons.setAllureLifecycle("Delete a Booking that Does not exist");
        try {
            Response response = bookingService.deleteBookingDetails(100);
            Commons.logger("Response Status Code is :" + response.statusCode());
            Assert.assertEquals(response.statusCode(),500);
            Commons.logger("Response Data is :" + response.jsonPath().get());
            Assert.assertEquals(response.jsonPath().getString("error"),"Failed to delete booking");
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    private int getBookingIdToDelete(BookingServiceImpl bookingService) {
        Map<String, Integer> params = Map.of("roomid", 1);
        Response response = bookingService.getBookingDetails(params);
        Commons.logger("Response Status Code is :" + response.statusCode());
        GetBookingByRoomResponse getBookingByRoomResponse = response.as(GetBookingByRoomResponse.class);
        return getBookingByRoomResponse.getBookings().stream()
                .filter(b -> "Siva".equals(b.getFirstname()))
                .map(GetBookingByRoomResponse.Booking::getBookingid)
                .findFirst()
                .orElse(0);
    }

}
