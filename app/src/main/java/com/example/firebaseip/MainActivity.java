package com.example.firebaseip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.core.Context;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RadioButton yes,no;
    Button submit;
    String data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        submit=findViewById(R.id.submit);

        String deviceId=Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("ID", deviceId);

        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference add=firestore.collection("user").document(deviceId);
        CollectionReference getData=firestore.collection("user");


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(yes.isChecked())
                {
                    data="yes";
                }

                if(no.isChecked())
                {
                    data="no";
                }

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(yes.isChecked())
                {
                    data="yes";
                }

                if(no.isChecked())
                {
                    data="no";
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> user = new HashMap<>();
                user.put("select",data);
                user.put("Id", deviceId);

                getData.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            String docId=documentSnapshot.getString("Id");
                            Log.d("Tag",docId);

                            if(docId.equals(deviceId))
                            {
                                Toast.makeText(MainActivity.this, "User Already Registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                add.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }
                    }
                });

            }
        });


    }
}