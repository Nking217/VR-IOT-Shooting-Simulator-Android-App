package com.example.vr_iot_shooting_simulator_android_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class StatisticsFragment extends Fragment {

    private static final String TAG = "StatisticsFragment";

    private SeekBar mSeekBar;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        mSeekBar = view.findViewById(R.id.seekBar);

        // Declare a FirebaseFirestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get a reference to the document containing the array
        DocumentReference docRef = db.collection("users").document("hitaccur");

        // Get the array from the document
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // Get the array from the document snapshot
                ArrayList<Long> array = (ArrayList<Long>) documentSnapshot.get("hitaccur");

                // Calculate the average of the elements in the array
                long sum = 0;
                for (long num : array) {
                    sum += num;
                }
                double average = sum / (double) array.size();

                // Update the SeekBar with the average value
                int progress = (int) Math.round(average);
                mSeekBar.setProgress(progress);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
                Log.w(TAG, "Error getting document", e);
            }
        });
        return view;
    }
}