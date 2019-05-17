package com.example.lynford.personal_notes_app.helper;

public class User {

    public static final  String DB_NAME = "notes.db";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "users";

    public static final String KEY_ID = "id";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + USER_TABLE + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_FULLNAME + " TEXT,"
            + KEY_USERNAME + " TEXT,"
            + KEY_PASSWORD + " TEXT" + ")";
}
