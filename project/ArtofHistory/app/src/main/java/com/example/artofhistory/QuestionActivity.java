package com.example.artofhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artofhistory.models.Task;
import com.example.artofhistory.utils.DatabaseHelper;

public class QuestionActivity extends AppCompatActivity {
    TextView headerQuestionTextView, textQuestionTextView;
    EditText answerEditText;
    Button checkButton;

    DatabaseHelper db;
    Task task = new Task();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        headerQuestionTextView = findViewById(R.id.headerQuestionTextView);
        textQuestionTextView = findViewById(R.id.textQuestionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        checkButton = findViewById(R.id.checkButton);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            task.setIdTask(arguments.getInt("id_task"));
            task.setName(arguments.getString("name"));
            task.setQuestion(arguments.getString("question"));
            task.setAnswer(arguments.getString("answer"));
            task.setLastAttempt(arguments.getString("last_attempt"));
            task.setIsDone(arguments.getBoolean("is_done"));
        } else {
            Toast.makeText(this, "Err", Toast.LENGTH_LONG).show();
            finish();
        }

        if (task.getLastAttempt() != null) answerEditText.setHint(task.getLastAttempt());
        answerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButton.setEnabled(!answerEditText.getText().toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        db = new DatabaseHelper(QuestionActivity.this);
        checkButton.setEnabled(false);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateTaskLastAttempt(task.getIdTask(), answerEditText.getText().toString().trim().toLowerCase());
                if (answerEditText.getText().toString().trim().toLowerCase().equals(task.getAnswer())) {
                    Toast.makeText(QuestionActivity.this, "Правильный ответ!", Toast.LENGTH_LONG).show();
                    db.updateTaskIsDone(task.getIdTask(), true);
                    finish();
                } else {
                    Toast.makeText(QuestionActivity.this, "Неправильный ответ!", Toast.LENGTH_LONG).show();
                    answerEditText.setHint(answerEditText.getText().toString());
                    answerEditText.setText(null);
                }
            }
        });

        headerQuestionTextView.setText(task.getName());
        textQuestionTextView.setText(task.getQuestion());
    }
}