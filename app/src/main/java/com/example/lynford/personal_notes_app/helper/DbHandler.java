package com.example.lynford.personal_notes_app.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lynford.personal_notes_app.model.ModelNotes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DbHandler extends SQLiteOpenHelper{

    private static final  String DB_NAME = "notesmanager.db";
    private static final int DB_VERSION = 1;

    /**
     * User Table
     */
    User user;
    Notes notes;


    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(User.CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(Notes.CREATE_TABLE_NOTES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + User.USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Notes.TABLE_NOTES);
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

   /////////////notes manager//////////



    /**
     * Insert or Add Notes
     * @param modelNotes
     */
    public void add_notes(ModelNotes modelNotes){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Notes.KEY_USERNAME,modelNotes.getUsername());
        contentValues.put(Notes.KEY_TITLE,modelNotes.getTitle());
        contentValues.put(Notes.KEY_DESCRIPTION,modelNotes.getDescription());
        contentValues.put(Notes.KEY_DATE, get_date_time());

        database.insert(Notes.TABLE_NOTES,null,contentValues);
    }

    /**
     * get all records from notes
     */
    public ArrayList<ModelNotes>getAllNotes(String username){

        ArrayList<ModelNotes>list = new ArrayList<ModelNotes>();
        SQLiteDatabase database = getReadableDatabase();

        String sql = "SELECT * FROM " + Notes.TABLE_NOTES + " WHERE username = ? ORDER BY id DESC";
        Cursor cursor = database.rawQuery(sql,new String[]{username});

        if (cursor.moveToFirst()){

            do {

                ModelNotes notes = new ModelNotes();

                notes.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Notes.KEY_ID))));
                notes.setTitle(cursor.getString(cursor.getColumnIndex(Notes.KEY_TITLE)));
                notes.setDescription(cursor.getString(cursor.getColumnIndex(Notes.KEY_DESCRIPTION)));
                notes.setDate_created(cursor.getString(cursor.getColumnIndex(Notes.KEY_DATE)));

                list.add(notes);

            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }

    /**
     * get single records
     */
    public ModelNotes getSIngleNotesRecords(int id){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM " + Notes.TABLE_NOTES + " WHERE id = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(id)});

        if (cursor != null)
            cursor.moveToNext();

        ModelNotes modelNotes = new ModelNotes();

        modelNotes.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Notes.KEY_ID))));
        modelNotes.setTitle(cursor.getString(cursor.getColumnIndex(Notes.KEY_TITLE)));
        modelNotes.setDescription(cursor.getString(cursor.getColumnIndex(Notes.KEY_DESCRIPTION)));
        modelNotes.setDate_created(cursor.getString(cursor.getColumnIndex(Notes.KEY_DATE)));

        cursor.close();
        sqLiteDatabase.close();
        return modelNotes;
    }


    /**
     * Delete Item
     */
    public void DeleteNotes(int id){

        SQLiteDatabase database = getWritableDatabase();
        database.delete(Notes.TABLE_NOTES,Notes.KEY_ID + "=?", new String[]{String.valueOf(id)});
        database.close();

    }


    /**
     * Update Notes
     */
    public void UpdateNotes(ModelNotes modelNotes){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Notes.KEY_TITLE,modelNotes.getTitle());
        contentValues.put(Notes.KEY_DESCRIPTION,modelNotes.getDescription());
        contentValues.put(Notes.KEY_DATE,modelNotes.getDate_created());
        database.update(Notes.TABLE_NOTES,contentValues,Notes.KEY_ID + "=?", new String[]{String.valueOf(modelNotes.getId())});

    }

    /**
     * get date time
     */
    private String get_date_time(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
             "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
