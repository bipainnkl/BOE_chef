package com.example.bipain.boe_restaurantapp.utils;

/**
 * Created by hoang on 01/04/2017.
 */

public class Constant {
    public static final String ID = "BOE";
    public static String API_ENDPOINT = "http://192.168.0.108:3000/";

    public final static String INVALID_USERNAME_ERROR = "Username length must be in from 8 to 40 characters";
    public final static String INVALID_PASSWORD_ERROR = "Invalid password";
    public final static String INVALID_PASSWORD_LENGTH_ERROR = "Password length must be in from 8 to 40 characters";
    public final static String INVALID_PASSWORD_NOT_MATCH_ERROR = "Password confirm is not match password";

    public final static String FIRST_NAME_PARAMS = "first_name";
    public final static String LAST_NAME_PARAMS = "last_name";
    public final static String USERNAME_PARAMS = "username";
    public final static String PASSWORD_PARAMS = "password";
    public final static String PASSWORD_CONFIRMATION_PARAMS = "password_confirmation";

    public final static String HEADER_ACCESS_TOKEN = "access-token";
    public final static String HEADER_EXPIRY = "expiry";
    public final static String HEADER_TOKEN_TYPE = "token-type";
    public final static String HEADER_UID = "uid";
    public final static String HEADER_CLIENT = "client";

    public final static int ROLE_CUSTOMER = 2;
    public final static int ROLE_CHEF = 2;

    public final static String SCANNER_RESULT_CONTENT = "SCANNER_RESULT_CONTENT";
    public final static String SCANNER_RESULT_QRCODE = "SCANNER_RESULT_QRCODE";

    public final static int MENU_TYPE_CART = 1;
    public final static int MENU_TYPE_HISTORY = 2;

    public final static String ROLE_DINER = "diner";

    public final static String MESSAGE_PERMISSION_LOGIN_ERROR = "Your account not have permission";

    public final static String KEY_DISH_DETAIL = "KEY_DISH_DETAIL";
    public final static String KEY_ID_CATEGORY = "KEY_ID_CATEGORY";

    public final static String KEY_TOTAL_PAYMENT = "KEY_TOTAL_PAYMENT";
    public final static String KEY_ORDER_NAME = "KEY_ORDER_NAME";
    public final static String KEY_ORDER_ITEM_TOTAL = "KEY_ORDER_ITEM_TOTAL";
    public final static String KEY_ORDER_ADAPTER = "KEY_ORDER_ADAPTER";

    public final static int IN_TIME = 0;
    public final static int OVER_TIME = -1;

    public final static String LOG_TAG = "WTF";

    public interface NotifyType{
        int Normal = 0;
        int Short = 1;
        int Warning = 2;
    }

    public final static String LANGUAGE_KEY = "LANGUAGE_KEY";
    public final static String VI_LANGUAGE_STRING = "vi";
    public final static String EN_LANGUAGE_STRING = "en";
}
