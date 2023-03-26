package com.dev.utils;

public class Errors {

    //ERRORS TO SIGNUP AND LOGIN
    public static final int ERROR_MISSING_USERNAME = 1000;
    public static final int ERROR_MISSING_PASSWORD = 1001;
    public static final int ERROR_WEAK_PASSWORD = 1002;
    public static final int ERROR_USERNAME_ALREADY_EXISTS = 1003;
    public static final int ERROR_WRONG_LOGIN_CREDS = 1004;
    public static final int ERROR_NO_SUCH_TOKEN = 1005;
    public static final int ERROR_NO_SUCH_USER = 1006;
    public static final int ERROR_NOT_ADMIN = 1007;
    public static final int ERROR_SYSTEM_ADMIN_CANT_LOGIN = 1008;

    //ERRORS TO MY PRODUCTS
    public static final int ERROR_NO_PRODUCTS_LIST = 1009;

    //ERRORS TO PRODUCT
    public static final int ERROR_NO_PRODUCT_DESCRIPTION = 1010;
    public static final int ERROR_NO_PRODUCT_IMG = 1011;
    public static final int ERROR_NO_PRODUCT_NAME = 1012;
    public static final int ERROR_NO_STARTING_PRICE_PRODUCT = 1013;
    public static final int ERROR_NO_3_OFFERS = 1014;
    public static final int ERROR_NO_SUCH_PRODUCT = 1015;
    public static final int ERROR_PRODUCT_STILL_FOR_SALE = 1016;

    //ERRORS TO OFFERS
    public static final int ERROR_LOWER_OFFER_AMOUNT = 1020;
    public static final int ERROR_LOWER_THAN_STARTING_PRICE = 1021;
    public static final int ERROR_PRODUCT_IS_NOT_FOR_SALE_ANYMORE= 1022;
    public static final int ERROR_NO_OFFERS_IN_THE_SYSTEM= 1023;
    public static final int ERROR_NO_ENOUGH_MONEY= 1024;
    public static final int ERROR_OFFER_DONT_WON_THE_SALE= 1025;

}
