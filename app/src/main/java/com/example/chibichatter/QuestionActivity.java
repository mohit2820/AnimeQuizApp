package com.example.chibichatter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuestionActivity extends AppCompatActivity {

    int flag = 0;
    int marks = 0;
    int correct = 0;
    int wrong = 0;

    // Dummy example data
    String[] question = {"What is the name of the massive sword Guts wields in Berserk?",
            "In Tokyo Ghoul, what is Kaneki's kagune type?",
            "Which anime features the concept of the 'Eclipse' ritual?",
            "In Claymore, what are the half-human, half-yoma warriors called?",
            "What triggers Eren Yeager's Titan transformation the first time?"
    };

    String[] options = {"Dragonslayer", "Nightbringer", "Demon Cutter", "Great Fang",
            "Rinkaku", "Koukaku", "Ukaku", "Bikaku",
            "Berserk", "Claymore", "Devilman Crybaby", "Attack on Titan",
            "Clays", "Hunters", "Claymores", "Yokai",
            "Touching a Titan", "Seeing Armin die", "Getting injured", "Injecting Titan serum"
    };

    int[] images = {R.drawable.sample,R.drawable.tokoyo,R.drawable.eclipse,R.drawable.clay,R.drawable.eren1
    };

    String[] answers = {"Dragonslayer","Rinkaku","Berserk","Claymores","Getting injured"
    };

    TextView score, quitBtn, disNo, questions;
    Button btn1, btn2, btn3, btn4, next;
    ImageView questionImage;

    Button selectedButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        quitBtn = findViewById(R.id.text_quit);
        score = findViewById(R.id.text_score);
        disNo = findViewById(R.id.text_progress);
        btn1 = findViewById(R.id.btn_option1);
        btn2 = findViewById(R.id.btn_option2);
        btn3 = findViewById(R.id.btn_option3);
        btn4 = findViewById(R.id.btn_option4);
        next = findViewById(R.id.btn_next);
        questions = findViewById(R.id.text_question);
        questionImage = findViewById(R.id.image_question);

        loadQuestion();

        View.OnClickListener optionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedButton = (Button) v;

                // Reset backgrounds
                btn1.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                btn2.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                btn3.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                btn4.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));

                // Highlight selected button
                selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            }
        };

        btn1.setOnClickListener(optionClickListener);
        btn2.setOnClickListener(optionClickListener);
        btn3.setOnClickListener(optionClickListener);
        btn4.setOnClickListener(optionClickListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedButton == null) {
                    Toast.makeText(QuestionActivity.this, "Please select an option first", Toast.LENGTH_SHORT).show();
                } else {
                    String selectedAnswer = selectedButton.getText().toString();
                    if (selectedAnswer.equals(answers[flag])) {
                        correct++;
                        marks += 1;
                    } else {
                        wrong++;
                    }

                    flag++;
                    selectedButton = null;

                    if (flag < question.length) {
                        loadQuestion();
                    } else {
                        Intent intent = new Intent(QuestionActivity.this , ResultScreen.class);
                        intent.putExtra("Correct" , correct);
                        intent.putExtra("Wrong" , wrong);
                        intent.putExtra("Tried" , flag);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

//         quit button working
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionActivity.this , ResultScreen.class);
                intent.putExtra("Correct" , correct);
                intent.putExtra("Wrong" , wrong);
                intent.putExtra("Tried" , flag);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestion() {
        questions.setText(question[flag]);
        questionImage.setImageResource(images[flag]);
        disNo.setText((flag + 1) + "/" + question.length);
        score.setText("Score: " + marks);

        // Set 4 options per question
        int base = flag * 4;
        btn1.setText(options[base]);
        btn2.setText(options[base + 1]);
        btn3.setText(options[base + 2]);
        btn4.setText(options[base + 3]);

        // Reset button colors
        btn1.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        btn2.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        btn3.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        btn4.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
    }
}
