package com.example.workallocation.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.workallocation.R;
import com.example.workallocation.Entrypoint.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {

               Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
               SplashScreen.this.startActivity(mainIntent);
               SplashScreen.this.finish();
           }
       },2000);
    }
}