package com.example.vr_iot_shooting_simulator_android_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    TextView textFullName, textCoins, texttestformap;
    String rtvFullName;
    Map rtvHistory;
    Map fixedHistory;
    long rtvCoins;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textFullName = (TextView)view.findViewById(R.id.textView6);
        textCoins = (TextView)view.findViewById(R.id.coins);
        texttestformap =(TextView)view.findViewById(R.id.testformap);


        mAuth = FirebaseAuth.getInstance();



        if(mAuth.getCurrentUser() != null){
            rtvFullName = mAuth.getCurrentUser().getDisplayName();
            textFullName.setText("Welcome " + rtvFullName);
        }
        else{
            Toast.makeText(getContext(), "Error = no users found", Toast.LENGTH_SHORT).show();
        }


        db = FirebaseFirestore.getInstance();
        // mDatabase.child("users").child(userId).setValue(user);
        db.collection("users")
                .document(rtvFullName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            //List<Map<String, Object>> mapsList = new ArrayList<>();

                            if(documentSnapshot != null && documentSnapshot.exists()){
                                Map<String, Object> map = documentSnapshot.getData();
                                rtvCoins = documentSnapshot.getLong("coins");
                                rtvHistory = map;
                                rtvHistory.remove("coins");

                                textCoins.setText("Coins = " + rtvCoins);
                                texttestformap.setText(rtvHistory.toString());

                                for(int i = 0; i < rtvHistory.size(); i++){

                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error = " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


        return view;
    }
}