package com.tinqin.library.book.rest.config;

public class Endpoints {
    public static String[] WHITELISTED_ENDPOINTS = {

//     "/api/v1/**"
            "/swagger/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/v3/api-docs*/**", /// needed for swagger
            "/actuator/**",
            "/instances/**",
            "/v3/**",
            "/error"
    };


    public static String[] ADMINENDPOINTSPOST = {

            "/books"



    };

}
