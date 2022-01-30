package com.example.rentx.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentx.R;
import com.example.rentx.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextView haveAccount;
    private FirebaseAuth mAuth;
    private EditText userName, userEmail, userPassword, confirmPassword;
    private AppCompatButton signUpButton;
    private DatabaseReference mRef;
    private String profile_pic_link = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        haveAccount = findViewById(R.id.have_account);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        confirmPassword = findViewById(R.id.user_confirm_password);
        signUpButton = findViewById(R.id.sign_up_button);



        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();




        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userName.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if(userEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
                else if(userPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(!userPassword.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
                    Toast.makeText(SignUpActivity.this, "Please Enter the Same Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(emailChecker(userEmail.getText().toString().trim())){
                        createUser(userEmail.getText().toString().trim(),
                                userPassword.getText().toString().trim(),
                                userName.getText().toString().trim());
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });




    }

    boolean emailChecker(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    void createUser(String email, String password, String name){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                User user = new User(name, email, profile_pic_link);


                if(task.isSuccessful()){


                    // save data in Firebase Database with automatic generated key
                    // push() function for ==> automatic key generation.
                    mRef.child("users").push().setValue(user);
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                    finish();
                    Toast.makeText(SignUpActivity.this, "User Created Successfully..", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Fail",Toast.LENGTH_SHORT).show();
             }
        });

    }


}