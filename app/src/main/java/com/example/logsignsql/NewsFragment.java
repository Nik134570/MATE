package com.example.logsignsql;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    com.example.logsignsql.MainActivity main;


    FirebaseAuth auth;
    private String time, place, description, name;
    TextView time1, place1, description1, name1;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        main = new com.example.logsignsql.MainActivity();
        final View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        auth = FirebaseAuth.getInstance();

        name1 = rootView.findViewById(R.id.name_data);
        place1 = rootView.findViewById(R.id.place_data);
        time1 = rootView.findViewById(R.id.time_data);
        description1 = rootView.findViewById(R.id.description_data);


        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference().child("Posts");
        Log.i("g1", "ok");
        referenceProfile.child("2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    name = snapshot.child("name").getValue().toString();
                    name1.setText(name);

                    place= snapshot.child("place").getValue().toString();
                    place1.setText(place);

                    time = snapshot.child("time").getValue().toString();
                    time1.setText(time);

                    description = snapshot.child("description").getValue().toString();
                    description1.setText(description);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("g3", "45");
            }
        });
        return rootView;
    }

}