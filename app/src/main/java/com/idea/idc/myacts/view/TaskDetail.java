package com.idea.idc.myacts.view;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.adapter.TaskItemAdapter;
import com.idea.idc.myacts.adapter.TaskListAdapter;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.Aktivitas;
import com.idea.idc.myacts.entity.TaskItem;
import com.idea.idc.myacts.entity.TaskList;
import com.idea.idc.myacts.view.fragment.TaskFragment;

import java.util.List;

public class TaskDetail extends AppCompatActivity {

    public static AppDatabase db;
    private RecyclerView recyclerView;
    private TaskItemAdapter taskItemAdapter;
    private EditText txt_name, txt_desc;
    private Button btn_add_task;
    private List<TaskItem> taskItems;
    private Dialog dialog;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();
        String message = intent.getStringExtra(TaskFragment.ID_MESSAGE);
        id = Integer.valueOf(message);

        txt_name = findViewById(R.id.txt_name);
        txt_desc = findViewById(R.id.txt_desc);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"aktivitas")
                .allowMainThreadQueries().build();

        TaskList task = db.taskListDao().getTaskById(id);

        txt_name.setText(task.getName());
        txt_desc.setText(task.getDescription());

        taskItems = db.taskItemDao().getTaskItemById(id);

        // seting recyclerview
        recyclerView = findViewById(R.id.recycler_taskItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        taskItemAdapter = new TaskItemAdapter(taskItems);

        taskItemAdapter.setOnCallbackListener(new TaskItemAdapter.OnCallbackListener() {
            @Override
            public void onClick(TaskItem task) {
                showDialogUpdate(task);
            }
        });

        recyclerView.setAdapter(taskItemAdapter);

        btn_add_task = findViewById(R.id.btn_add_task);
        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTambah();
            }
        });
    }

    private void showDialogTambah(){
        dialog = new AppCompatDialog(this);
        dialog.setContentView(R.layout.dialog_task_item);
        dialog.setTitle("Tambah Kegiatan");

        final EditText ed_taskItem_nama = dialog.findViewById(R.id.ed_taskItem_name);
        final EditText ed_taskItem_desc = dialog.findViewById(R.id.ed_taskItem_desc);
        final EditText ed_taskItem_tanggal = dialog.findViewById(R.id.ed_taskItem_tanggal);
        final EditText ed_taskItem_waktu = dialog.findViewById(R.id.ed_taskItem_waktu);


        Button btn_hapus = dialog.findViewById(R.id.btn_hapus);
        btn_hapus.setVisibility(View.GONE);

        Button btn_simpan = dialog.findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskItem task = new TaskItem();
                task.setId_list(id);
                task.setName(ed_taskItem_nama.getText().toString());
                task.setDesc(ed_taskItem_desc.getText().toString());
                task.setDate(ed_taskItem_tanggal.getText().toString());
                task.setTime(ed_taskItem_waktu.getText().toString());
                task.setStatus("0");

                db.taskItemDao().insertTaskItem(task);
                Toast.makeText(getApplicationContext(),"Data ditambahkan", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                taskItems.clear();
                taskItems.addAll(db.taskItemDao().getTaskItemById(id));
                taskItemAdapter.notifyDataSetChanged();
            }
        });

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void showDialogUpdate(TaskItem task){
        dialog = new AppCompatDialog(this);
        dialog.setContentView(R.layout.dialog_task_item);
        dialog.setTitle("Tambah Kegiatan");

        final TextView txt_id = dialog.findViewById(R.id.txt_id);
        final EditText ed_taskItem_nama = dialog.findViewById(R.id.ed_taskItem_name);
        final EditText ed_taskItem_desc = dialog.findViewById(R.id.ed_taskItem_desc);
        final EditText ed_taskItem_tanggal = dialog.findViewById(R.id.ed_taskItem_tanggal);
        final EditText ed_taskItem_waktu = dialog.findViewById(R.id.ed_taskItem_waktu);

        txt_id.setText(String.valueOf(task.getId_task()));
        ed_taskItem_nama.setText(task.getName());
        ed_taskItem_desc.setText(task.getDesc());
        ed_taskItem_tanggal.setText(task.getDate());
        ed_taskItem_waktu.setText(task.getTime());

        Button btn_simpan = dialog.findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskItem task = new TaskItem();
                task.setId_task(Integer.valueOf(txt_id.getText().toString()));
                task.setId_list(id);
                task.setName(ed_taskItem_nama.getText().toString());
                task.setDesc(ed_taskItem_desc.getText().toString());
                task.setDate(ed_taskItem_tanggal.getText().toString());
                task.setTime(ed_taskItem_waktu.getText().toString());
                task.setStatus("0");

                db.taskItemDao().updateTaskItem(task);
                Toast.makeText(getApplicationContext(),"Data ditambahkan", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                taskItems.clear();
                taskItems.addAll(db.taskItemDao().getTaskItemById(id));
                taskItemAdapter.notifyDataSetChanged();
            }
        });

        Button btn_hapus = dialog.findViewById(R.id.btn_hapus);
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskItem task = new TaskItem();
                task.setId_task(Integer.valueOf(txt_id.getText().toString()));
                task.setId_list(id);
                task.setName(ed_taskItem_nama.getText().toString());
                task.setDesc(ed_taskItem_desc.getText().toString());
                task.setDate(ed_taskItem_tanggal.getText().toString());
                task.setTime(ed_taskItem_waktu.getText().toString());
                task.setStatus("0");

                db.taskItemDao().deleteTaskItem(task);
                Toast.makeText(getApplicationContext(),"Data dihapus", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                taskItems.clear();
                taskItems.addAll(db.taskItemDao().getTaskItemById(id));
                taskItemAdapter.notifyDataSetChanged();
            }
        });

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
}
