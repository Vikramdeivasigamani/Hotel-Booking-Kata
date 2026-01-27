package com.api;

public class URL {

    public static String base_url  ="https://automationintesting.online/api/";

    public static String post_url_to_create_token  = base_url+"auth/login";
    public static String post_url_to_create_reservation  = base_url+"booking";
    public static String get_url_to_retrive_booking_room  = base_url+"booking";
    public static String delete_booking_reservation  = base_url+"booking/{bookingid}";
    public static String put_url_to_update_booking_reservation  = base_url+"booking";

}
