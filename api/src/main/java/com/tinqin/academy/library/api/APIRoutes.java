package com.tinqin.academy.library.api;

public class APIRoutes {
    public static final String API = "/api/v1";

    public static final String API_BOOK = API + "/book";
    public static final String GET_BOOK = API_BOOK + "/{bookId}";
    public static final String DELETE_BOOK = API_BOOK + "/{bookId}";
    public static final String API_BOOK_BY_AUTHOR = API + "/book/by-author/{authorId}";

    public static final String API_AUTHOR = API + "/author";
    public static final String GET_AUTHOR = API_AUTHOR + "/{authorId}";

    public static final String API_USER = API + "/user";
    public static final String BLOCK_USER = API_USER + "/{userId}";



}
