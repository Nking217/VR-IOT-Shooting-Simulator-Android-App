package com.example.vr_iot_shooting_simulator_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;

    private Button signIN;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);


        signIN = (Button) findViewById(R.id.button);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = (EditText) findViewById((R.id.password));

        mAuth = FirebaseAuth.getInstance();
    }



    private void userLogin(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        Log.d("Email", "userLogin: " + email);
        if(email.isEmpty()){
            editTextEmail.setError("Email Is Required!");
            editTextEmail.requestFocus();
        }
//        else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            editTextEmail.setError("Please Enter A Valid Email!");
//            editTextEmail.requestFocus();
//        }
        else if(password.isEmpty()){
            editTextPassword.setError("Password Is Required!");
            editTextPassword.requestFocus();
        }
        else if(password.length() < 6){
            editTextPassword.setError("Please Enter At Least 6 Characters!");
            editTextPassword.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginScreen.this, "User has logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginScreen.this, HomeActivity.class));
                    }
                    else{
                        Toast.makeText(LoginScreen.this, "Log in error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    public void login(View view) {

        userLogin();


    }
}