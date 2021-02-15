package com.komiut.conductor.sessionmanager;

/**
 * Created by peter on 25/12/2020.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


import com.komiut.conductor.model.Trips;
import com.komiut.conductor.utils.ObjectSerializer;

import java.io.IOException;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Komiut";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USER = "user";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(Trips trips){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USER, ObjectSerializer.serialize(trips));

        // commit changes
        editor.commit();
    }


    /**
     * Get stored session data
     * */
    public Trips getTripDetails(){
        Trips user = new Trips();
        try {
            user = (Trips) ObjectSerializer.deserialize(pref.getString(KEY_USER, ObjectSerializer.serialize(user)));
        } catch (IOException e) {
            Log.d("ERROR LOG", "printStackTraceD");
        } catch (ClassNotFoundException e) {
            Log.d("ERROR LOG", "printStackTraceD");
        }

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
