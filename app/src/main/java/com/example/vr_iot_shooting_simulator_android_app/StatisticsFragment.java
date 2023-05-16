package com.example.vr_iot_shooting_simulator_android_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;


public class StatisticsFragment extends Fragment {

    private static final String TAG = "StatisticsFragment";
    private ProgressBar progressBar;

    String rtvFullName;
    Map rtvHistory;
    Map map;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    public int headshotsum, bodywidesum, bodycentersum, hitaccuracysum;
    public int headshotpercentagetotal, bodywidepercentagetotal, bodycenterpercentagetotal, hitaccuracypercentagetotal;
    public int cnt;
    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        progressBar.findViewById(R.id.HitAccuracyProgressBar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //progressBar.setProgress(50);
        //progressBar.setMax(100);


        if(mAuth.getCurrentUser() != null){
            rtvFullName = mAuth.getCurrentUser().getDisplayName();
        }
        else{
            Toast.makeText(getContext(), "Error = no users found", Toast.LENGTH_SHORT).show();
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
                                Map<String, Object> map = documentSnapshot.getData();
                                rtvHistory = map;
                                rtvHistory.remove("coins");

                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    String hitAccuracy, bodyShotWidePercentage, headShotPercentage, bodyShotCenterPercentage, gameTime;
                                    String[] arrOfStr = entry.getValue().toString().split(",");
                                    for(int x = 0; x < arrOfStr.length; x++){
                                        if(arrOfStr[x].contains("{")){
                                            arrOfStr[x] = arrOfStr[x].replace("{","");

                                        }
                                        else if(arrOfStr[x].contains("}")){
                                            arrOfStr[x] = arrOfStr[x].replace("}", "");
                                        }
                                        Log.d("Data", arrOfStr[x]);
                                    }
                                    for(int y = 0; y < arrOfStr.length; y++){
                                        Log.d("Data", arrOfStr[y]);

                                    }
                                    cnt = arrOfStr.length;
                                    String[] bodycenterarr = arrOfStr[0].split("=");
                                    bodycentersum += Integer.parseInt(bodycenterarr[1]);

                                    String[] headshotarr = arrOfStr[1].split("=");
                                    headshotsum += Integer.parseInt(headshotarr[1]);

                                    String[] hitaccurarr = arrOfStr[2].split("=");
                                    hitaccuracysum += Integer.parseInt(hitaccurarr[1]);

                                    String[] bodycwidearr = arrOfStr[3].split("=");
                                    bodywidesum += Integer.parseInt(bodycwidearr[1]);
                                    /*
                                    String[] gametimearr = arrOfStr[4].split("=");
                                    gameTime = gametimearr[1].toString();
                                    */
                                    //+ entry.getValue() + "\n"
                                    //arrayList.add("Game - " + entry.getKey() + "\n" + "Body shot Center = " + bodyShotCenterPercentage + "\n" + "HeadShot = " + headShotPercentage + "\n" + "Hit Accuracy = " + hitAccuracy + "\n" + "Body shot wide = " + bodyShotWidePercentage); //Prints value on the array list
                                }
                                // -----------------------------------------------
                                // ------------ Values For Percentage ------------
                                // ------------ In the progress bar --------------
                                // -----------------------------------------------
                                bodycenterpercentagetotal = bodycentersum/cnt;
                                bodywidepercentagetotal = bodywidesum/cnt;
                                headshotpercentagetotal = headshotsum/cnt;
                                hitaccuracypercentagetotal = hitaccuracysum/cnt;


                                //Crashing not because of this
                                progressBar.setProgress(hitaccuracypercentagetotal);
                                progressBar.setMax(100);


                            }
                        }
                    }
                });
        return view;
    }
}