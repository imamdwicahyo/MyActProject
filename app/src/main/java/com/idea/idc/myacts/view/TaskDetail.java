package com.idea.idc.myacts.view;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.TaskList;
import com.idea.idc.myacts.view.fragment.TaskFragment;

public class TaskDetail extends AppCompatActivity {

    public static AppDatabase db;
    EditText txt_name, txt_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();
        String message = intent.getStringExtra(TaskFragment.ID_MESSAGE);

        txt_name = findViewById(R.id.txt_name);
        txt_desc = findViewById(R.id.txt_desc);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"aktivitas")
                .allowMainThreadQueries().build();

        TaskList task = db.taskListDao().getTaskById(Integer.valueOf(message));

        txt_name.setText(task.getName());
        txt_desc.setText(task.getDescription());
    }
}
