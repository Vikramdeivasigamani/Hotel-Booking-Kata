package com.booking.models;

import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {
    public static final Properties prop = new Properties();
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final String BASE_URL;


    static {
        try {
            prop.load(ConfigReader.class.getResourceAsStream("/" + "config.properties"));
            USERNAME = prop.getProperty("username");
            PASSWORD = prop.getProperty("password");
            BASE_URL = prop.getProperty("baseUrl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
