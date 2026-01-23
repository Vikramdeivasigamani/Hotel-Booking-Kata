package com.booking.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude()

@JsonPropertyOrder({
        "bookings"
})

public class GetBookingByRoomResponse {

    @JsonProperty("bookings")
    private List<Booking> bookings;
    public GetBookingByRoomResponse() {}
    public GetBookingByRoomResponse(List<Booking> bookings) {
        super();
        this.bookings = bookings;
    }

    @JsonProperty("bookings")
    public List<Booking> getBookings() {
        return bookings;
    }

    @JsonProperty("bookings")
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @JsonPropertyOrder({
            "bookingid",
            "roomid",
            "firstname",
            "lastname",
            "depositpaid",
            "bookingdates"
    })

    public static class Booking {

        @JsonProperty("bookingid")
        private Integer bookingid;

        @JsonProperty("roomid")
        private Integer roomid;
        @JsonProperty("firstname")
        private String firstname;
        @JsonProperty("lastname")
        private String lastname;
        @JsonProperty("depositpaid")
        private Boolean depositpaid;
        @JsonProperty("bookingdates")
        private BookingDates bookingdates;

        public Booking() {
        }

        public Booking(Integer bookingid, Integer roomid, String firstname, String lastname, Boolean depositpaid, String email, String phone, BookingDates bookingdates) {
            super();
            this.bookingid = bookingid;
            this.roomid = roomid;
            this.firstname = firstname;
            this.lastname = lastname;
            this.depositpaid = depositpaid;
            this.bookingdates = bookingdates;

        }

        @JsonProperty("bookingid")
        public Integer getBookingid() {
            return bookingid;
        }

        @JsonProperty("bookingid")
        public void setbookingid(Integer bookingid) {
            this.bookingid = bookingid;
        }

        @JsonProperty("roomid")
        public Integer getRoomid() {
            return roomid;
        }

        @JsonProperty("roomid")
        public void setRoomid(Integer roomid) {
            this.roomid = roomid;
        }

        @JsonProperty("firstname")
        public String getFirstname() {
            return firstname;
        }

        @JsonProperty("firstname")
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        @JsonProperty("lastname")
        public String getLastname() {
            return lastname;
        }

        @JsonProperty("lastname")
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        @JsonProperty("depositpaid")
        public Boolean getDepositpaid() {
            return depositpaid;
        }

        @JsonProperty("depositpaid")
        public void setDepositpaid(Boolean depositpaid) {
            this.depositpaid = depositpaid;
        }

        @JsonProperty("bookingdates")
        public BookingDates getBookingdates() {
            return bookingdates;
        }

        @JsonProperty("bookingdates")
        public void setBookingDates(BookingDates bookingdates) {
            this.bookingdates = bookingdates;
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonPropertyOrder({
                "checkin",
                "checkout"
        })

        public static class BookingDates {

            public BookingDates(){}
            public BookingDates(String checkin, String checkout) {
                super();
                this.checkin = checkin;
                this.checkout = checkout;
            }

            @JsonProperty("checkin")
            private String checkin;
            @JsonProperty("checkout")
            private String checkout;

            @JsonProperty("checkin")
            public String getCheckin() {
                return checkin;
            }

            @JsonProperty("checkin")
            public void setCheckin(String checkin) {
                this.checkin = checkin;
            }

            @JsonProperty("checkout")
            public String getCheckout() {
                return checkout;
            }

            @JsonProperty("checkout")
            public void setCheckout(String checkout) {
                this.checkout = checkout;
            }

        }

    }
}