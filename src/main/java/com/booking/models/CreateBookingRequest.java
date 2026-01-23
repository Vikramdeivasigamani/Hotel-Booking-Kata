package com.booking.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude()
@JsonPropertyOrder({
        "roomid",
        "firstname",
        "lastname",
        "depositpaid",
        "email",
        "phone",
        "bookingdates"
})

public class CreateBookingRequest {

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
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;

    public CreateBookingRequest(Integer roomid, String firstname, String lastname, Boolean depositpaid, String email, String phone, BookingDates bookingdates) {
        super();
        this.roomid = roomid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.email = email;
        this.phone = phone;
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
    public Boolean getDepositPaid() {
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

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonInclude()
    @JsonPropertyOrder({
            "checkin",
            "checkout"
    })

    public static class BookingDates {

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