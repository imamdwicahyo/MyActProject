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
import android.widget.Toast;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.adapter.TaskItemAdapter;
import com.idea.idc.myacts.adapter.TaskListAdapter;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.TaskItem;
import com.idea.idc.myacts.entity.TaskList;
import com.idea.idc.myacts.view.TaskDetail;

import java.util.List;

public class HomeFragment extends Fragment {

    public static AppDatabase db;
    private RecyclerView recyclerView;
    private TaskItemAdapter taskItemAdapter;
    private List<TaskItem> taskItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = Room.databaseBuilder(getContext(),AppDatabase.class,"aktivitas")
                .allowMainThreadQueries().build();

        taskItems = db.taskItemDao().getAll();
        recyclerView = view.findViewById(R.id.recycler_taskHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskItemAdapter = new TaskItemAdapter(taskItems);
        recyclerView.setAdapter(taskItemAdapter);



        return view;

    }
}
