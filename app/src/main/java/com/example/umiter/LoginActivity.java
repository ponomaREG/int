package com.example.umiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.umiter.databinding.ActivityLoginBinding;
import com.example.umiter.databinding.ActivitySignupBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    TextInputLayout layoutPassword;
    TextInputLayout layoutEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword()){

                }else{
                    checkUser();
                }
            }
        });


        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateEmail(){
        layoutEmail = binding.TextInputLayoutEmail;
        String val = binding.eTextEmail.getText().toString();
        if(val.isEmpty()){
            layoutEmail.setError("Email cannot be empty");
            return false;
        }else{
            layoutEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        layoutPassword = binding.TextInputLayoutPassword;
        String val = binding.eTextPassword.getText().toString();
        if(val.isEmpty()){
            layoutPassword.setError("Password cannot be empty");
            return false;
        }else{
            layoutPassword.setError(null);
            return true;
        }
    }

    private void checkUser(){
        layoutEmail = binding.TextInputLayoutEmail;
        layoutPassword = binding.TextInputLayoutPassword;
        String userEmail = binding.eTextEmail.getText().toString().trim();
        String userPassword = binding.eTextPassword.getText().toString().trim();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = ref.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    layoutEmail.setError(null);
                    String passwordFromDB = snapshot.child(userEmail).child("password").getValue(String.class);
                    //String passwordFromDB = snapshot.child(userEmail).child("password").getValue(String.class);
                    //System.out.println(passwordFromDB);
                    if(!Objects.equals(passwordFromDB, userPassword)){
                        layoutEmail.setError(null);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        layoutPassword.setError("Invalid data");
                        binding.eTextPassword.requestFocus();
                    }
                }else{
                    layoutEmail.setError("User dose not exist");
                    binding.eTextEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}