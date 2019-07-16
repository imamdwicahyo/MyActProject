package com.idea.idc.myacts.view.fragment;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.adapter.TaskAdapter;
import com.idea.idc.myacts.adapter.TaskAdapter2;
import com.idea.idc.myacts.adapter.TaskAdapter3;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.Aktivitas;
import com.idea.idc.myacts.model.AktivitasModel;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

    private Aktivitas aktivitas;
    public static AppDatabase db;
    private RecyclerView recyclerView;
    private TaskAdapter2 taskAdapter;
    private TaskAdapter3 taskAdapter3;
    List<Aktivitas> aktivitasList = new ArrayList<>();
    ArrayList<AktivitasModel> listAktivitas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        db = Room.databaseBuilder(getContext(),AppDatabase.class,"aktivitas").allowMainThreadQueries().build();

        aktivitasList = db.aktivitasDao().getAll();

        addTask();

        recyclerView = view.findViewById(R.id.recycler_task);
        taskAdapter3 = new TaskAdapter3((ArrayList<Aktivitas>) aktivitasList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(taskAdapter3);


        return view;
    }

    void addTask(){
        listAktivitas = new ArrayList<>();
        listAktivitas.add(new AktivitasModel(1,"membaca111","0000"));
        listAktivitas.add(new AktivitasModel(2,"membaca2","0000"));
    }
}
