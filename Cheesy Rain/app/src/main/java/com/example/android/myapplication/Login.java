package com.example.android.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class Login extends AppCompatActivity {
    CheeseView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = new CheeseView(this);
        setContentView(myView);
    }

}
