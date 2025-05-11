package com.example.chibichatter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginPage extends AppCompatActivity {

    EditText userEmail , userPassword;
    Button login;
    TextView title1 , title2 , signup;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userEmail = findViewById(R.id.input_name);
        userPassword = findViewById(R.id.input_email);
        login = findViewById(R.id.btn_create_account);
        title1 = findViewById(R.id.title_anime);
        title2 = findViewById(R.id.title_quiz);
        signup = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginPage.this , SignUpPage.class));
                finish();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUserEmail = userEmail.getText().toString().trim();
                String mUserPassword = userPassword.getText().toString();

                if(TextUtils.isEmpty(mUserEmail)){
                    userEmail.setError("Please Enter Your Email");
                    return;
                }

                if(TextUtils.isEmpty(mUserPassword)){
                    userPassword.setError("Please Enter Your Password");
                    return;
                }

                if(mUserPassword.length()<8){
                    userPassword.setError("Too Short To Secure Anything");
                }

                mAuth.signInWithEmailAndPassword(mUserEmail , mUserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(loginPage.this , "You’ve entered the Eclipse..." ,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(loginPage.this , MainActivity.class));
                            finishAffinity();
                        }

                        else {
                            Toast.makeText(loginPage.this , "The path is blocked. Check your credentials" ,Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }
}