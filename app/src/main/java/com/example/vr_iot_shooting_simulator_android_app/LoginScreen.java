package com.example.vr_iot_shooting_simulator_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class LoginScreen extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private Button signIN;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);


        signIN = (Button) FindViewById(R.id.signIn);
        signIN.SetOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById((R.id.password);
    }
}