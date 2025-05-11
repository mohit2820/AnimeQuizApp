package com.example.chibichatter;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import app.rive.runtime.kotlin.RiveAnimationView;

public class SignUpPage extends AppCompatActivity {

    public  static  final  String TAG = "TAG";
    EditText userName , userEmail , userPassword;
    Button register;
    TextView title1 , title2;

    ImageView logingriffith;

    RiveAnimationView loadingAnimation;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userName = findViewById(R.id.input_name);
        userEmail = findViewById(R.id.input_email);
        userPassword = findViewById(R.id.input_password);
        register = findViewById(R.id.btn_create_account);
        title1 = findViewById(R.id.title_anime);
        title2 = findViewById(R.id.title_quiz);
        logingriffith = findViewById(R.id.logingriffith);
        loadingAnimation = findViewById(R.id.loading);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();


//        user is already registered
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(SignUpPage.this , MainActivity.class));
            finish();
        }

        logingriffith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this , loginPage.class));
                finish();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUserName = userName.getText().toString();
                String mUserEmail = userEmail.getText().toString().trim();
                String mPassword = userPassword.getText().toString();

                if(TextUtils.isEmpty(mUserName)){
                    userName.setError("Please Enter Your Name");
                    return;
                }

                if(TextUtils.isEmpty(mUserEmail)){
                    userEmail.setError("Please Enter Your Email");
                    return;
                }

                if(TextUtils.isEmpty(mPassword)){
                    userPassword.setError("Please Enter Your Password");
                    return;
                }

                if(mPassword.length()<8){
                    userPassword.setError("Too Short To Secure Anything");
                }

                loadingAnimation.setVisibility(VISIBLE);

                mAuth.createUserWithEmailAndPassword(mUserEmail , mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            mUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SignUpPage.this , "You’ve entered the Eclipse..." ,Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG , "That email’s not worthy of the Hawk" + e.getMessage());
                                }
                            });

                            DocumentReference documentReference = mStore.collection("users").document(mUser.getUid());
                            Map<String , Object> user = new HashMap<>();
                            user.put("Name" , mUserName);
                            user.put("Email" , mUserEmail);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG , "User Profile Created");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.d(TAG , "Failed" + e.getMessage());
                                }
                            });

                            Toast.makeText(SignUpPage.this , "Through the darkness, a new hero emerges. Welcome!" ,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpPage.this , MainActivity.class));
                            finishAffinity();

                        }

                        else {
                            Toast.makeText(SignUpPage.this , "The brand of sacrifice has cursed you. Try again." ,Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });



    }
}