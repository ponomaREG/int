package com.example.umiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.umiter.databinding.ActivitySignupBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextInputLayout layoutPassword;
    TextInputLayout layoutName;
    TextInputLayout layoutEmail;
    TextInputEditText edPassword;
    TextInputEditText edEmail;
    TextInputEditText edName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users"); //перенес из createNewUser
        layoutEmail = binding.TextInputLayoutEmail;
        binding.eTextEmail.addTextChangedListener(passwordWatcher);
        layoutName = binding.TextInputLayoutName;
        binding.eTextName.addTextChangedListener(passwordWatcher); // bilo nameWatcher
        layoutPassword = binding.TextInputLayoutPassword;
        binding.eTextPassword.addTextChangedListener(passwordWatcher);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });


        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createNewUser() {

        String name = binding.eTextName.getText().toString().trim();
        String email = binding.eTextEmail.getText().toString().trim();
        String password = binding.eTextPassword.getText().toString().trim();

        HelperClass helperClass = new HelperClass(name, email, password);
        myRef.child(name).setValue(helperClass);
        //myRef.push().setValue(helperClass); // будет с генерированым идентификатором

        Toast.makeText(SignupActivity.this, "Successful login", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String name = binding.eTextName.getText().toString().trim();
            String email = binding.eTextEmail.getText().toString().trim();
            String password = binding.eTextPassword.getText().toString().trim();

            if(!validateName(name) | !validateEmail(email) | !validatePassword(password)){
                binding.signupButton.setEnabled(false);
            }else {
                binding.signupButton.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String password = binding.eTextPassword.getText().toString().trim();
        }
    };

    private boolean validatePassword(String passwordInput){
        if (passwordInput.length() >= 8) {
            if(passwordInput.length() <= 10) {
                Pattern pattern = Pattern.compile("[^\\w]");
                Matcher matcher = pattern.matcher(passwordInput);
                if (matcher.find()) {
                    layoutPassword.setHelperText("Strong Password");
                    layoutPassword.setError(null);
                    return true;

                } else {
                    layoutPassword.setHelperText(null);
                    layoutPassword.setError("Weak Password. Not enough special characters(!@/#+%)");
                    return false;
                }
            }else{
                layoutPassword.setHelperText(null);
                layoutPassword.setError("Max characters 10");
                return false;
            }
        } else {
            layoutPassword.setHelperText("Enter Minimum 8 characters");
            layoutPassword.setError(null);
            return false;
        }
    }

    private boolean validateName(String nameInput){
        if (nameInput.isEmpty()) {
            layoutName.setError("Username cannot be empty");
            return false;
        } else if (nameInput.length() > 15) {
            layoutName.setError("Max characters 15");
            return false;
        } else {
            layoutName.setError(null);
            return true;
        }
    }

    private boolean validateEmail(String emailInput){
        if(emailInput.isEmpty()){
            layoutEmail.setError("Email cannot be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            layoutEmail.setError("Please enter a valid email address");
            return false;
        }else{
            layoutEmail.setError(null);
            return true;
        }
    }
}
