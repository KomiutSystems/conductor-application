package com.komiut.conductor.sessionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.komiut.conductor.MainActivity;
import com.komiut.conductor.retrofit.LoginResponse;


public class Sessionmangementsharedpref {

    private static final String SHARED_PREF_NAME = "sessionPref";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String TOKEN_TYPE = "token_type";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String EXPIRES_AT = "expires_at";

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String CELLPHONE = "phone";
    private static final String EMAIL = "email";

    private static final String SACCONAME = "sacconame";
    private static final String SACCOID = "saccoid";
    private static final String PLATE = "plate";
    private static final String TILL = "till";
    private static final String CUSTOMERCARENO = "customercareno";
    private static final String COMPANYLOGAN = "companyslogan";


    private static Sessionmangementsharedpref mInstance;
    private static Context mCtx;

    private Activity activity;

    public Sessionmangementsharedpref(Context context) {
        mCtx = context;
    }

    public static synchronized Sessionmangementsharedpref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Sessionmangementsharedpref(context);
        }
        return mInstance;
    }


    public void userLogin(LoginResponse userCredentials) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, userCredentials.getAccessToken());
        editor.putString(TOKEN_TYPE, userCredentials.getTokenType());
        editor.putString(EXPIRES_AT, userCredentials.getExpiresAt());
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();
    }

    public void updateAccessToken(String accessToken, String expiresAt) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.putString(EXPIRES_AT, expiresAt);
        editor.apply();
    }

    public void storeAccessToken(String accessToken) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public void storeUserDetails(int id, String username, String firstname, String lastname, String phone, String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ID, id);
        editor.putString(USERNAME, username);
        editor.putString(FIRSTNAME, firstname);
        editor.putString(LASTNAME, lastname);
        editor.putString(CELLPHONE, phone);
        editor.putString(EMAIL, email);
        editor.apply();
    }


    public void saveSaccoDetails(String selectedSaccoId, String selectedTill, String selectedCustomerCareNo, String selectedCompanySlogan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SACCOID, Integer.parseInt(selectedSaccoId));
        editor.putString(TILL, selectedTill);
        editor.putString(CUSTOMERCARENO, selectedCustomerCareNo);
        editor.putString(COMPANYLOGAN, selectedCompanySlogan);
        editor.apply();

    }

    public void savePlate(String selectedPlate) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PLATE, selectedPlate);
        editor.apply();
    }

    public int getSaccoId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SACCOID, 0);
    }

    public String getPlate() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PLATE, null);
    }

    public String getTill() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TILL, null);
    }

    public String getCustomercareno() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TILL, null);
    }

    public String getCompanyslogan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TILL, null);
    }


    public String getUserPhone() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CELLPHONE, null);

    }

    //this method will give the logged in user
    public LoginResponse getUserCredentials() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new LoginResponse(

                sharedPreferences.getString(ACCESS_TOKEN, null),
                sharedPreferences.getString(TOKEN_TYPE, null),
                sharedPreferences.getInt(EXPIRES_AT, 0)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(mCtx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);

    }

    public String getAccessToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public String saveSaccoName(String saccoName) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SACCONAME, null);
    }

    public String getSaccoName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SACCONAME, null);
    }
}
