package com.example.lynford.personal_notes_app.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.lynford.personal_notes_app.model.ModelUsers;

public class MySharedPreferences {

    private String TAG = MySharedPreferences.class.getSimpleName();

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "notes";

    private static final String KEY_USERNAME = "username";

    public MySharedPreferences(Context context) {

        this._context = context;

        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void storeUser(ModelUsers modelUsers){

        editor.putString(KEY_USERNAME,modelUsers.getUsername());
        editor.commit();
        Log.e(TAG,"user stored in preferences" + modelUsers);

    }

    public ModelUsers getUser(){

        if (pref.getString(KEY_USERNAME,null) != null){

            String uname;
            uname = pref.getString(KEY_USERNAME, null);

            ModelUsers modelUsers = new ModelUsers(uname);
            return modelUsers;
        }


        return null;
    }

    /**
     * Clear All
     */
    public void clearAll(){
        editor.clear();
        editor.commit();
    }

}
