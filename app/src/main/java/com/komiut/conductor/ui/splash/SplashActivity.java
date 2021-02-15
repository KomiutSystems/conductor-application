package com.komiut.conductor.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.komiut.conductor.HomeActivity;
import com.komiut.conductor.MainActivity;
import com.komiut.conductor.R;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String isFirstTimeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences=getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
        editor=sharedPreferences.edit();
        isFirstTimeUser=sharedPreferences.getString(getString(R.string.first_time_user),"null");



        new Handler().postDelayed(() -> {
            Intent intent=new Intent(SplashActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finishAffinity();
        },3000);
    }
}