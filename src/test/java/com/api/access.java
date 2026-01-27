package com.api;

import java.util.ResourceBundle;

public class access {


    public static ResourceBundle getCredentials()
    {
        ResourceBundle information= ResourceBundle.getBundle("info"); // Load properties file (named 'info'
        return information;
    }
}
