package com.idea.idc.myacts.view.fragment;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.adapter.AktivitasAdapter;
import com.idea.idc.myacts.adapter.TaskAdapter2;
import com.idea.idc.myacts.adapter.TaskAdapter3;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.Aktivitas;
import com.idea.idc.myacts.model.AktivitasModel;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment{

    public static AppDatabase db;
    private RecyclerView recyclerView;
    private AktivitasAdapter aktivitasAdapter;
    private List<Aktivitas> aktivitasList = new ArrayList<>();
    private AppCompatDialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_task, container, false);
        aktivitasList = onLoad(view,db); // ambil data dari database

        // seting recyclerview
        recyclerView = view.findViewById(R.id.recycler_task);
        aktivitasAdapter = new AktivitasAdapter(aktivitasList);
        aktivitasAdapter.setOnCallbackListener(new AktivitasAdapter.OnCallbackListener() {
            @Override
            public void onClick(Aktivitas aktivitas) {
                Toast.makeText(getContext(),aktivitas.getKegiatan()+" ditekan",Toast.LENGTH_SHORT).show();
                showDialogUpdate(aktivitas);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(aktivitasAdapter);


        return view;
    }


    // fungsi ini untuk mengambil data dari database (tabel aktivitas)
    // lalu memasukannya kedalam list array 'aktivitasList'
    private List<Aktivitas> onLoad(View view, AppDatabase appDatabase){
        appDatabase = Room.databaseBuilder(getContext(),AppDatabase.class,"aktivitas")
                .allowMainThreadQueries().build();

        //mengisi data aktivitas List
        List<Aktivitas> act = appDatabase.aktivitasDao().getAll();
        return act;
    }

    private void showDialogUpdate(Aktivitas aktivitas){
        dialog = new AppCompatDialog(getContext());
        dialog.setContentView(R.layout.dialog_aktivitas);
        dialog.setTitle("Ubah Aktifitas");

        final EditText ed_kegiatan = dialog.findViewById(R.id.ed_kegiatan);
        ed_kegiatan.setText(aktivitas.getKegiatan());

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
}
