package com.idea.idc.myacts.view.fragment;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.adapter.TaskItemAdapter;
import com.idea.idc.myacts.adapter.TaskListAdapter;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.TaskItem;
import com.idea.idc.myacts.entity.TaskList;
import com.idea.idc.myacts.view.TaskDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    public static AppDatabase db;
    private RecyclerView recyclerView;
    private TaskItemAdapter taskItemAdapter;
    private List<TaskItem> taskItems;
    private ImageView btn_today, btn_week, btn_month;
    private SimpleDateFormat formatDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = Room.databaseBuilder(getContext(),AppDatabase.class,"aktivitas")
                .allowMainThreadQueries().build();

        btn_today = view.findViewById(R.id.btn_today);
        btn_week = view.findViewById(R.id.btn_week);
        btn_month = view.findViewById(R.id.btn_month);

        formatDate = new SimpleDateFormat("dd-MM-yyyy");
        final String date = formatDate.format(new Date());

        taskItems = db.taskItemDao().getAll();

        recyclerView = view.findViewById(R.id.recycler_taskHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskItemAdapter = new TaskItemAdapter(taskItems);
        recyclerView.setAdapter(taskItemAdapter);

        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date tanggal = null;
                try {
                    tanggal = formatDate.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                taskItems.clear();
                taskItems.addAll(db.taskItemDao().getAllByDate(tanggal));
                taskItemAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), date , Toast.LENGTH_SHORT).show();
            }
        });
        btn_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String week = addDate(7,date);
                Date tanggal = null;
                Date minggu = null;
                try {
                    tanggal = formatDate.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    minggu = formatDate.parse(week);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                taskItems.clear();
                taskItems.addAll(db.taskItemDao().getAllByDateBetween(tanggal,minggu));
                taskItemAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),week, Toast.LENGTH_SHORT).show();
            }
        });
        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = addDate(30,date);
                Date tanggal = null;
                Date bulan = null;
                try {
                    tanggal = formatDate.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    bulan = formatDate.parse(month);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                taskItems.clear();
                taskItems.addAll(db.taskItemDao().getAllByDateBetween(tanggal,bulan));
                taskItemAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),month, Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    public String addDate(int i,String dt){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(formatDate.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, i);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        String output = formatDate.format(c.getTime());
        return output;
    }
}
