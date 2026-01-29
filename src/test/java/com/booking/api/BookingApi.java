package com.booking.api;

import com.booking.stepdefinitions.AuthenticationSteps;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingApi {
    public Response getBooking(int id, String token) {
        return given()
                .log().all()
                .contentType("application/json")
                .header("Cookie", "token=" + token)
                .when()
                .get("/booking/" + id);
    }

    public Response getBookingByIdWithoutAuth(int id) {
        return given()
                .log().all()
                .contentType("application/json")
                .when()
                .get("/booking/" + id);
    }

    public Response createBooking(Map<String, Object> body, String token) {
        return given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post("/booking");
    }

    public Response updateBooking(int id, Map<String, Object> body, String token) {
        return given()
                .log().all()
                .contentType("application/json")
                .header("Cookie", "token=" + token)
                .body(body)
                .when()
                .put("/booking/" + id);
    }

    public Response updateBookingWithoutAuth(int id, Map<String, Object> body) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(body)
                .when()
                .put("/booking/" + id);
    }


    public Response patchBooking(int id, Map<String, Object> body, String token) {
        return given()
                .log().all()
                .contentType("application/json")
                .header("Cookie", "token=" + token)
                .body(body)
                .when()
                .patch("/booking/" + id);
    }

    public Response patchBookingWithoutAuth(int id, Map<String, Object> body) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(body)
                .when()
                .patch("/booking/" + id);
    }

    public Response deleteBooking(int id, String token) {
        return given()
                .log().all()
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/" + id);
    }

    public Response deleteBookingWithoutAuth(int id){
        return given()
                .log().all()
                .when()
                .delete("/booking/" + id);
    }

    public Response login(String username, String password) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(Map.of(
                        "username", username,
                        "password", password
                ))
                .when()
                .post("/auth/login");
    }
}