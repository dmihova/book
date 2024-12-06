package com.tinqin.academy.library.api;

public class APIRoutes {
    public static final String API = "/api/v1";

    public static final String API_BOOK = API + "/books";
    public static final String GET_BOOK = API_BOOK + "/{bookId}";
    public static final String DELETE_BOOK = API_BOOK + "/{bookId}";
    public static final String API_BOOK_BY_AUTHOR = API_BOOK + "/by-author/{authorId}";

    public static final String API_AUTHOR = API + "/authors";
    public static final String GET_AUTHOR = API_AUTHOR + "/{authorId}";

    public static final String API_USER = API + "/users";
    public static final String BLOCK_USER = API_USER + "/block/{userId}";
    public static final String UNBLOCK_USER = API_USER + "/unblock/{userId}";


    public static final String API_SUBSCRIPTION = API + "/subscriptions";
    public static final String GET_SUBSCRIPTION = API_SUBSCRIPTION + "/{subscriptionId}";
    public static final String DELETE_SUBSCRIPTION = API_SUBSCRIPTION + "/{subscriptionId}";


    public static final String API_RENTAL = API + "/rentals";
    public static final String GET_RENTAL = API_RENTAL + "/{rentalId}";
    public static final String DELETE_RENTAL = API_RENTAL + "/{rentalId}";
    public static final String API_RENTAL_RENT = API_RENTAL + "/rent";
    public static final String API_RENTAL_RETURN = API_RENTAL + "/return";


    public static final String API_PURCHASE = API + "/purchases";
    public static final String GET_PURCHASE = API_PURCHASE + "/{purchaseId}";










}
