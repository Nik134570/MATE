package com.example.logsignsql;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Logger;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    boolean flag1 = false;
    boolean flag2 = false;
    boolean flag3 = false;
    boolean flag4 = false;

    String[] inf = new String[7];

    Dialog dialog;

    private String username, surusername;

    EditText timer, namer, placer, descripter;
    com.example.logsignsql.MainActivity main;


    FirebaseAuth auth;
    FirebaseUser user;
    TextView namik, surnamik;

    Button AddPostButton;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        main = new com.example.logsignsql.MainActivity();
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        auth = FirebaseAuth.getInstance();
        dialog = new Dialog(getActivity());
        user = auth.getCurrentUser();
        namik = rootView.findViewById(R.id.namik);
        surnamik = rootView.findViewById(R.id.surnamik);
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference().child("Users");
        Log.i("g1", "ok");
        referenceProfile.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    username = snapshot.child("username").getValue().toString();
                    namik.setText(username);
                    surusername = snapshot.child("username-2").getValue().toString();
                    surnamik.setText(surusername);
                    String username1 = username + "1";
                    Log.i("g2", username1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("g3", "45");
            }
        });

        AddPostButton = rootView.findViewById(R.id.add_post_button);
        timer = rootView.findViewById(R.id.time_email);
        namer = rootView.findViewById(R.id.namers_email);
        placer = rootView.findViewById(R.id.place_email);
        descripter = rootView.findViewById(R.id.description_email);

        AddPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String time = timer.getText().toString();
                String name = namer.getText().toString();
                String place = placer.getText().toString();
                String description = descripter.getText().toString();

                DatabaseReference datareference = FirebaseDatabase.getInstance().getReference("Posts").child("1");
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("name", name);
                hashMap.put("time", time);
                hashMap.put("place", place);
                hashMap.put("description", description);

                datareference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        showDialog();
                    }
                });
            }
        });



        ImageButton imageButton1 = rootView.findViewById(R.id.voiting1);;
        TextView textButton1 = rootView.findViewById(R.id.voitingtext1);;
        ImageButton imageButton2 = rootView.findViewById(R.id.voiting2);;
        TextView textButton2 = rootView.findViewById(R.id.voitingtext2);;
        ImageButton imageButton3 = rootView.findViewById(R.id.voiting3);;
        TextView textButton3 = rootView.findViewById(R.id.voitingtext3);;
        ImageButton imageButton4 = rootView.findViewById(R.id.voiting4);;
        TextView textButton4 = rootView.findViewById(R.id.voitingtext4);;


        imageButton1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // меняем изображение на кнопке
                if (flag1) {
                    imageButton1.setImageResource(R.drawable.vote1);
                    textButton1.setTextColor(0xAAD9D9D9);
                }
                else {
                    // возвращаем первую картинку
                    imageButton1.setImageResource(R.drawable.vote2);
                    textButton1.setTextColor(0xAA31A742);
                }
                flag1 = !flag1;
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // меняем изображение на кнопке
                if (flag2) {
                    imageButton2.setImageResource(R.drawable.vote1);
                    textButton2.setTextColor(0xAAD9D9D9);
                }
                else {
                    // возвращаем первую картинку
                    imageButton2.setImageResource(R.drawable.vote2);
                    textButton2.setTextColor(0xAA31A742);
                }
                flag2 = !flag2;
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // меняем изображение на кнопке
                if (flag3) {
                    imageButton3.setImageResource(R.drawable.vote1);
                    textButton3.setTextColor(0xAAD9D9D9);
                }
                else {
                    // возвращаем первую картинку
                    imageButton3.setImageResource(R.drawable.vote2);
                    textButton3.setTextColor(0xAA31A742);
                }
                flag3 = !flag3;
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // меняем изображение на кнопке
                if (flag4) {
                    imageButton4.setImageResource(R.drawable.vote1);
                    textButton4.setTextColor(0xAAD9D9D9);
                }
                else {
                    // возвращаем первую картинку
                    imageButton4.setImageResource(R.drawable.vote2);
                    textButton4.setTextColor(0xAA31A742);
                }
                flag4 = !flag4;
            }
        });


        return rootView;
    }



    private void showDialog(){
        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}