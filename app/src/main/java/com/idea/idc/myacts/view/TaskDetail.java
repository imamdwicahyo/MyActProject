package com.idea.idc.myacts.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.adapter.TaskItemAdapter;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.TaskItem;
import com.idea.idc.myacts.entity.TaskList;
import com.idea.idc.myacts.view.fragment.TaskFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TaskDetail extends AppCompatActivity {

    public static AppDatabase db;
    private RecyclerView recyclerView;
    private TaskItemAdapter taskItemAdapter;
    private EditText txt_name, txt_desc;
    private Button btn_add_task;
    private Button btn_update_list;
    private Button btn_hapus_list;
    private List<TaskItem> taskItems;
    private Dialog dialog;
    private TaskList task;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat dateFormatter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        //get id task list
        final Intent intent = getIntent();
        String message = intent.getStringExtra(TaskFragment.ID_MESSAGE);
        id = Integer.valueOf(message);

        txt_name = findViewById(R.id.txt_name);
        txt_desc = findViewById(R.id.txt_desc);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"aktivitas")
                .allowMainThreadQueries().build();

        // get data task
        task = db.taskListDao().getTaskById(id);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

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

        btn_update_list = findViewById(R.id.btn_update_list);
        btn_update_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setId_list(id);
                task.setName(txt_name.getText().toString());
                task.setDescription(txt_desc.getText().toString());
                db.taskListDao().updateTaskList(task);

                Toast.makeText(getApplicationContext(), "Data Berhasil di ubah", Toast.LENGTH_SHORT).show();
            }
        });

        btn_hapus_list = findViewById(R.id.btn_hapus_list);
        btn_hapus_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.taskListDao().deleteTaskList(task);

                Intent intent1 = new Intent(TaskDetail.this, HomeActivity.class);
                intent1.putExtra("DATAINTENT","task_fragment");
                startActivity(intent1);

                Toast.makeText(getApplicationContext(),"Data berhasil di ubah", Toast.LENGTH_SHORT).show();
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

        ed_taskItem_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(ed_taskItem_tanggal, dialog.getContext());
            }
        });

        ed_taskItem_waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(ed_taskItem_waktu, dialog.getContext());
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

    private void showDateDialog(final EditText editText, Context context){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                editText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showTimeDialog(final EditText editText, Context context){
        Calendar newCalendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                editText.setText(+hourOfDay+":"+minute);
            }
        },
                newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(context));

        timePickerDialog.show();
    }
}
