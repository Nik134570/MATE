package com.example.logsignsql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth myAuth;
    EditText signupName, signupEmail, signupSurname, signupPassword, signupPhone, signupGender, signupConfirm;
    TextView loginRedirectText, showText;
    Button signupButton, showDialog;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.name_users);
        signupEmail = findViewById(R.id.signup_email);
        signupSurname = findViewById(R.id.surname_users);
        signupPassword = findViewById(R.id.signup_password);
        signupPhone = findViewById(R.id.phone_number);
        signupGender = findViewById(R.id.gender_users);
        signupConfirm = findViewById(R.id.signup_confirm);

        showText = findViewById(R.id.show_text);

        signupButton = findViewById(R.id.signup_button);
        showDialog = findViewById(R.id.show_dialog);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myAuth = FirebaseAuth.getInstance();

                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String surname = signupSurname.getText().toString();
                String password = signupPassword.getText().toString();
                String phone = signupPhone.getText().toString();
                String gender = signupGender.getText().toString();
                String confirmPassword = signupConfirm.getText().toString();

                String date = showText.getText().toString();

                if (email.equals("")||password.equals("")||confirmPassword.equals("")||name.equals("")||
                        surname.equals("")||phone.equals("")||gender.equals("")||date.equals("Choose date")){
                    Toast.makeText(SignupActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (confirmPassword.equals(password)) {
                        if(email.length()>=10) {
                            Log.i("email", email.substring(email.length() - 6, email.length()));
                            if (email.substring(email.length() - 10, email.length()).equals("@gmail.com") || email.substring(email.length() - 8, email.length()).equals("@mail.ru")) {
                                if (password.length() >= 8) {

                                    register(name, email, password, surname, phone, gender, date);


                                    Toast.makeText(SignupActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignupActivity.this, "Password is short!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignupActivity.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignupActivity.this, "Email is short!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignupActivity.this, "Invalid Confirm Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void openDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        showText.setText(String.valueOf(year) + "." + String.valueOf(month+1) + "." + String.valueOf(dayOfMonth));
                    }
                },
                2023,
                0,
                1);
        datePickerDialog.show();

    }

    private void register(final String name, String email, String password, String surname, String phone, String gender, String date){
        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = myAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", name);
                            hashMap.put("username-2", surname);
                            hashMap.put("phone", phone);
                            hashMap.put("gender", gender);
                            hashMap.put("date", date);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", name.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignupActivity.this, "You can't register worth this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}

