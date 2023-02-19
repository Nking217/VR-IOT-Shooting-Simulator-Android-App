package com.example.vr_iot_shooting_simulator_android_app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    TextView textFullName, textCoins;
    String rtvFullName;
    long rtvCoins;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textFullName = findViewById(R.id.textView6);
        textCoins = findViewById(R.id.Coins);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        if(mAuth.getCurrentUser() != null){
            rtvFullName = mAuth.getCurrentUser().getDisplayName();
            textFullName.setText("Welcome " + rtvFullName);
        }
        else{
            Toast.makeText(HomeActivity.this, "Error = no users found", Toast.LENGTH_SHORT).show();
        }

        db.collection("users")
            .document(rtvFullName)
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot != null && documentSnapshot.exists()){
                            rtvCoins = documentSnapshot.getLong("coins");
                            textCoins.setText("Coins = " + rtvCoins);

                        }
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HomeActivity.this, "Error = " + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });




    }





}