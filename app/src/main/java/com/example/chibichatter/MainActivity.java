package com.example.chibichatter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }



    public void darkFantasy(View view) {
        Intent intent = new Intent(MainActivity.this , QuestionActivity.class);
        startActivity(intent);
        finish();
    }

    public void scienceFiction(View view) {
    }


    public void sliceOfLife(View view) {
    }

    public void adventure(View view) {
    }


    public void horror(View view) {
    }

    public void Romance(View view) {
    }

    public void Action(View view){

    }

}

