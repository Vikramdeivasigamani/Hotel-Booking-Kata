package com.booking.stepdefinitions;

import com.payloads.Booking;
import com.payloads.Bookingdates;
import java.util.HashMap;
import java.util.Map;

public class ClientInformation {

    private Booking booking;
    Booking payload = new Booking();
    Bookingdates payloadDates = new Bookingdates();

    public ClientInformation(Booking booking){
        this.booking = booking;
    }

    public static Map<String,Object> getAllClientInfo(String roomid, String lastname, String firstname,Boolean depositpaid, String checkin, String checkout){

        Map<String,Object> info = new HashMap<String,Object>();
        info.put("roomid",roomid);
        info.put("firstname",firstname);
        info.put("lastname",lastname);
        info.put("depositpaid",depositpaid);
        Map<String,Object> dates= new HashMap<String,Object>();
        dates.put("checkin",checkin);
        dates.put("checkout",checkout);
        info.put("bookingdates",dates);

        return info;



    }


    public static Map<String,Object> updateClientNameAndLastName() {

        Map<String, Object> updated_info = new HashMap<String, Object>();
        updated_info.put("roomid", 1000);
        updated_info.put("firstname", "Jimmy");
        updated_info.put("lastname", "Love");
        updated_info.put("depositpaid", false);
        Map<String, Object> dates = new HashMap<String, Object>();
        dates.put("checkin", "2025-06-25");
        dates.put("checkout", "2025-07-02");
        updated_info.put("bookingdates", dates);

        return updated_info;

    }




}
