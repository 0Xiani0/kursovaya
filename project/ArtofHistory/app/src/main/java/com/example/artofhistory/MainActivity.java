package com.example.artofhistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.artofhistory.adapters.TasksAdapter;
import com.example.artofhistory.models.Task;
import com.example.artofhistory.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView headerTextView;
    RecyclerView questionsRecyclerView;

    DatabaseHelper db;
    List<Task> tasksList = new ArrayList<>();
    TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headerTextView = findViewById(R.id.headerTextView);
        questionsRecyclerView = findViewById(R.id.questionsRecyclerView);

        db = new DatabaseHelper(MainActivity.this);
        tasksAdapter = new TasksAdapter(MainActivity.this, db);
        tasksList = db.readTasks();

        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionsRecyclerView.setAdapter(tasksAdapter);

        tasksAdapter.setTasks(tasksList);
    }

    public void onResume() {
        super.onResume();
        tasksList = db.readTasks();
        tasksAdapter.setTasks(tasksList);
    }
}

