package com.booking.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import java.util.Optional;

public class ApiUtils extends TestSuiteSetup{

    private static void setBaseUri(String baseURI) {
        if (baseURI != null && !baseURI.isEmpty()) {
            RestAssured.baseURI = baseURI;
        }
    }
    private static boolean isValidMap(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    private static boolean isValidBody(Object body) {
        return !(body instanceof String s) || !s.isBlank();
    }

    private static RequestSpecification prepareRequest(Map<String, String> headers, Map<String, String> cookies, Object body) {
        RequestSpecification request = RestAssured.given().log().all();

        if (isValidMap(headers)) request.headers(headers);
        if (isValidMap(cookies)) request.cookies(cookies);
        if (body != null && isValidBody(body)) request.body(body);
        return request;
    }

    public static Response post(String baseURI, String postUrl, Map<String, String> headers, Object body) {
        setBaseUri(baseURI);
        return prepareRequest(headers, null,body)
                .post(postUrl)
                .then().log().all().extract().response();
    }

    public static Response put(String baseURI, String putUrl, Map<String, String> headers, Map<String, String> cookies, Object body) {
        setBaseUri(baseURI);
        return prepareRequest(headers, cookies, body)
                .put(putUrl)
                .then().log().all().extract().response();
    }

    public static Response get(String baseURI, String getUrl, Map<String, ?> params, Map<String, String> cookies, Map<String, String> headers) {
        setBaseUri(baseURI);
        RequestSpecification request = prepareRequest(headers, cookies, null);
        if (params != null && !params.isEmpty()) {
            request.params(params);
        }
        return request
                .get(getUrl)
                .then().log().all().extract().response();
    }

    public static Response delete(String baseURI, String deleteUrl, Map<String, String> cookies, Map<String, String> headers) {
        setBaseUri(baseURI);
        return prepareRequest(headers, cookies, null)
                .delete(deleteUrl)
                .then().log().all().extract().response();
    }
}
