package com.example.artofhistory.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artofhistory.QuestionActivity;
import com.example.artofhistory.R;
import com.example.artofhistory.models.Task;
import com.example.artofhistory.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TASK_VIEW = R.layout.item_question;

    private List<Task> tasksList = new ArrayList<>();
    Context context;
    DatabaseHelper db;

    public TasksAdapter(Context context, DatabaseHelper db) {
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == TASK_VIEW) viewHolder = new TaskViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TaskViewHolder) {
            final com.example.artofhistory.models.Task task = tasksList.get(position);
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
            String postscript;

            taskViewHolder.isDoneCheckBox.setChecked(task.getIsDone());
            taskViewHolder.isDoneCheckBox.setSelected(task.getIsDone());
            int [][] states = {{android.R.attr.state_checked}, {}};
            int [] colors = {Color.BLACK, Color.BLACK};
            taskViewHolder.isDoneCheckBox.setButtonTintList( new ColorStateList(states, colors));

            taskViewHolder.questionTextView.setText(task.getName());

            if(task.getIsDone()) {
                postscript = "Правильный ответ: ";
                taskViewHolder.questionCardView.setClickable(false);
            }
            else {
                postscript = "Прошлый ответ: ";
                taskViewHolder.questionCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, QuestionActivity.class);

                        intent.putExtra("id_task", task.getIdTask());
                        intent.putExtra("name", task.getName());
                        intent.putExtra("answer", task.getAnswer());
                        intent.putExtra("question", task.getQuestion());
                        intent.putExtra("last_attempt", task.getLastAttempt());
                        intent.putExtra("is_done", task.getIsDone());

                        context.startActivity(intent);
                    }
                });
            }

            if (task.getLastAttempt() != null) {
                taskViewHolder.lastAttemptTextView.setVisibility(View.VISIBLE);
                taskViewHolder.lastAttemptTextView.setHint(postscript + task.getLastAttempt());
            }
            else taskViewHolder.lastAttemptTextView.setVisibility(View.GONE);

            int[] gdColors = {Color.parseColor("#eeaeca"), Color.parseColor("#a0c1e8")};
            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gdColors);

            gd.setCornerRadius(30f);
            taskViewHolder.questionCardView.setBackground(gd);
        }
    }

    public int getItemViewType(int position) {
        return TASK_VIEW;
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public void setTasks(List<com.example.artofhistory.models.Task> tasksList){
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private CardView questionCardView;
        private TextView questionTextView, lastAttemptTextView;
        private CheckBox isDoneCheckBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            questionCardView = itemView.findViewById(R.id.questionCardView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            isDoneCheckBox = itemView.findViewById(R.id.isDoneCheckBox);
            lastAttemptTextView = itemView.findViewById(R.id.lastAttemptTextView);
        }
    }
}
