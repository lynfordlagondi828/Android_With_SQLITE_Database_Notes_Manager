package com.example.lynford.personal_notes_app.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper{

    /**
     * User Table
     */
    User user;


    public DbHandler(Context context) {
        super(context, User.DB_NAME, null, User.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(User.CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + User.USER_TABLE);
        onCreate(sqLiteDatabase);
    }


    /**
     * User Registration
     */
    public void user_registration(String fullname, String username, String password){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(User.KEY_FULLNAME,fullname);
        contentValues.put(User.KEY_USERNAME,username);
        contentValues.put(User.KEY_PASSWORD,password);

        database.insert(User.USER_TABLE,null,contentValues);
    }

    /**
     * User Authentication
     */
    public boolean user_authentication(String username, String password){

        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM " + User.USER_TABLE + " WHERE username =? AND password =? ";
        Cursor cursor = database.rawQuery(sql,new String[]{username,password});

        boolean object = false;

        if (cursor.moveToFirst()){
            object = true;
            int count = 0;

            while (cursor.moveToNext()){
                count++;
            }
        }

        cursor.close();
        return object;

    }

    /**
     * check email
     */
    public boolean check_username(String username){

        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM " + User.USER_TABLE + " WHERE username = ?";
        Cursor cursor = database.rawQuery(sql,new String[]{username});

        boolean object = false;
        if (cursor.moveToFirst()){
            object = true;

            int count = 0;

            while (cursor.moveToNext()){
                count++;
            }
        }

        cursor.close();
        return object;
    }
}
