package com.example.logsignsql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.logsignsql.databinding.ActivityLoginBinding;
import com.example.logsignsql.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    DatabaseReference reference;

    public static String emailUser, passwordUser, nameUser, surnameUser, genderUser, dateUser, phoneUser;

    int Color_white = Color.parseColor("#FFFFFF");
    int Color_hint = Color.parseColor("#a0acba");
    private int selectedTab = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        Log.i("s1", "1");
        firebaseUser = auth.getCurrentUser();
        Log.i("s2", "2");
        Log.i("d", firebaseUser.getEmail());

        final LinearLayout newsLayout = findViewById(R.id.newsLayout);
        final LinearLayout searchLayout = findViewById(R.id.searchLayout);
        final LinearLayout chatLayout = findViewById(R.id.chatLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);

        final ImageView newsImage = findViewById(R.id.newsicon);
        final ImageView searchImage = findViewById(R.id.serchicon);
        final ImageView chatImage = findViewById(R.id.chaticon);
        final ImageView profileImage = findViewById(R.id.profileicon);

        final TextView newsIext = findViewById(R.id.newstext);
        final TextView searchIext = findViewById(R.id.searchtext);
        final TextView chatIext = findViewById(R.id.chattext);
        final TextView profileIext = findViewById(R.id.profiletext);


        getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, NewsFragment.class, null)
                                        .commit();
        newsLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 1){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, NewsFragment.class, null)
                            .commit();


                    searchImage.setImageResource(R.drawable.baseline_search_24);
                    chatImage.setImageResource(R.drawable.baseline_message_24);
                    profileImage.setImageResource(R.drawable.baseline_people_24);

                    newsIext.setTextColor(Color_white);
                    newsImage.setImageResource(R.drawable.baseline_newspaper_focus_24);
                    newsLayout.setBackgroundResource(R.drawable.round_back_newspaper);

                    chatIext.setTextColor(Color_hint);
                    profileIext.setTextColor(Color_hint);
                    searchIext.setTextColor(Color_hint);

                    searchLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(400);
                    scaleAnimation.setFillAfter(true);
                    newsLayout.startAnimation(scaleAnimation);

                    selectedTab = 1;
                }
            }
        }));

        searchLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 2){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, SearchFragment.class, null)
                            .commit();


                    newsImage.setImageResource(R.drawable.baseline_newspaper_24);
                    chatImage.setImageResource(R.drawable.baseline_message_24);
                    profileImage.setImageResource(R.drawable.baseline_people_24);

                    searchIext.setTextColor(Color_white);
                    searchImage.setImageResource(R.drawable.baseline_search_focus_24);
                    searchLayout.setBackgroundResource(R.drawable.round_back_search);

                    chatIext.setTextColor(Color_hint);
                    profileIext.setTextColor(Color_hint);
                    newsIext.setTextColor(Color_hint);

                    newsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    chatLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(400);
                    scaleAnimation.setFillAfter(true);
                    searchLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;
                }
            }
        }));

        chatLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 3){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ChatFragment.class, null)
                            .commit();


                    newsImage.setImageResource(R.drawable.baseline_newspaper_24);
                    searchImage.setImageResource(R.drawable.baseline_search_24);
                    profileImage.setImageResource(R.drawable.baseline_people_24);

                    chatIext.setTextColor(Color_white);
                    chatImage.setImageResource(R.drawable.baseline_message_focus_24);
                    chatLayout.setBackgroundResource(R.drawable.round_back_chat);

                    newsIext.setTextColor(Color_hint);
                    profileIext.setTextColor(Color_hint);
                    searchIext.setTextColor(Color_hint);

                    searchLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    newsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(400);
                    scaleAnimation.setFillAfter(true);
                    chatLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;
                }
            }
        }));

        profileLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 4){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ProfileFragment.class, null)
                            .commit();


                    newsImage.setImageResource(R.drawable.baseline_newspaper_24);
                    searchImage.setImageResource(R.drawable.baseline_search_24);
                    chatImage.setImageResource(R.drawable.baseline_message_24);

                    profileIext.setTextColor(Color_white);
                    profileImage.setImageResource(R.drawable.baseline_people_focus_24);
                    profileLayout.setBackgroundResource(R.drawable.round_back_profile);

                    chatIext.setTextColor(Color_hint);
                    newsIext.setTextColor(Color_hint);
                    searchIext.setTextColor(Color_hint);

                    searchLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    newsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(400);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    selectedTab = 4;
                }
            }
        }));
    }



}