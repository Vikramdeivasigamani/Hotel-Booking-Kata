package com.booking.testdata;

import io.cucumber.datatable.DataTable;

import java.util.HashMap;
import java.util.Map;

public class BookingRequestBuilder {

    private final Map<String, String> data;

    private BookingRequestBuilder(Map<String, String> data) {
        this.data = data;
    }

    public static BookingRequestBuilder from(DataTable table) {
        return new BookingRequestBuilder(
                table.asMaps(String.class, String.class).get(0)
        );
    }

    public Map<String, Object> requestBody() {
        Map<String, Object> bookingDates = Map.of(
                "checkin", data.get("checkIn"),
                "checkout", data.get("checkOut")
        );

        return Map.of(
                "roomid", Integer.parseInt(data.get("roomid")),
                "firstname", data.get("firstname"),
                "lastname", data.get("lastname"),
                "depositpaid", Boolean.parseBoolean(data.get("depositpaid")),
                "bookingdates", bookingDates,
                "email", data.get("email"),
                "phone", data.get("phone")
        );
    }

    public Map<String, String> raw() {
        return new HashMap<>(data);
    }
}