package com.example.vr_iot_shooting_simulator_android_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class HistoryFragment extends Fragment {
    ArrayAdapter arrayAdapter;
    ListView listView;
    String rtvFullName;
    Map rtvHistory;
    Map map;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        listView = (ListView) view.findViewById(R.id.listview);

        ArrayList<String> arrayList = new ArrayList<>();

        if(mAuth.getCurrentUser() != null){
            rtvFullName = mAuth.getCurrentUser().getDisplayName();
        }
        else{
            Toast.makeText(getContext(), "Error = no users found", Toast.LENGTH_SHORT).show();
        }
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
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
                                    String[] bodycenterarr = arrOfStr[0].split("=");
                                    bodyShotCenterPercentage = bodycenterarr[1].toString();

                                    String[] headshotarr = arrOfStr[1].split("=");
                                    headShotPercentage = headshotarr[1].toString();

                                    String[] hitaccurarr = arrOfStr[2].split("=");
                                    hitAccuracy = hitaccurarr[1].toString();

                                    String[] bodycwidearr = arrOfStr[3].split("=");
                                    bodyShotWidePercentage = bodycwidearr[1].toString();
                                    /*
                                    String[] gametimearr = arrOfStr[4].split("=");
                                    gameTime = gametimearr[1].toString();
                                    */

                                    //+ entry.getValue() + "\n"
                                    arrayList.add("Game - " + entry.getKey() + "\n" + "Body shot Center = " + bodyShotCenterPercentage + "\n" + "HeadShot = " + headShotPercentage + "\n" + "Hit Accuracy = " + hitAccuracy + "\n" + "Body shot wide = " + bodyShotWidePercentage); //Prints value on the array list
                                }
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

//        arrayList.add("game1");
//        arrayList.add("game2");



         listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext(), "clicked item" + i + " " + arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}