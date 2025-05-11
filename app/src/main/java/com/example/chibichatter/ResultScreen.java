package com.example.chibichatter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultScreen extends AppCompatActivity {

    TextView correctCount , wrongCount , triedCount;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        correctCount = findViewById(R.id.correctCount);
        wrongCount = findViewById(R.id.wrongCount);
        triedCount = findViewById(R.id.triedCount);
        button = findViewById(R.id.button);

        Intent intent = getIntent();
        int correct = intent.getIntExtra("Correct" , 0);
        int wrong = intent.getIntExtra("Wrong" , 0);
        int tried = intent.getIntExtra("Tried" , 0);

        correctCount.setText(String.valueOf(correct));
        wrongCount.setText(String.valueOf(wrong));
        triedCount.setText(String.valueOf(tried));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultScreen.this , MainActivity.class));
                finishAffinity();
            }
        });
    }
}