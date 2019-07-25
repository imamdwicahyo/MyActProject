package com.idea.idc.myacts.view.fragment;

import android.arch.persistence.room.Room;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.adapter.AktivitasAdapter;
import com.idea.idc.myacts.adapter.TaskListAdapter;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.Aktivitas;
import com.idea.idc.myacts.entity.TaskList;
import com.idea.idc.myacts.view.TaskDetail;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment{

    public static final String ID_MESSAGE = "com.idea.idc.myacts.ID";

    public static AppDatabase db;
    private RecyclerView recyclerView;
    private TaskListAdapter taskListAdapter;
    private List<TaskList> taskLists;
    private AktivitasAdapter aktivitasAdapter;
    private List<Aktivitas> aktivitasList = new ArrayList<>();
    private AppCompatDialog dialog;

    private ImageView btn_add;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_task, container, false);

        db = Room.databaseBuilder(getContext(),AppDatabase.class,"aktivitas")
                .allowMainThreadQueries().build();


        taskLists = db.taskListDao().getAll();

        // seting recyclerview
        recyclerView = view.findViewById(R.id.recycler_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        taskListAdapter = new TaskListAdapter(taskLists);

        taskListAdapter.setOnCallbackListener(new TaskListAdapter.OnCallbackListener() {
            @Override
            public void onClick(TaskList task) {
                Toast.makeText(getContext()," ditekan",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), TaskDetail.class);
                String id_message = String.valueOf(task.getId_list());
                intent.putExtra(ID_MESSAGE, id_message);
                startActivity(intent);
            }
        });
;
        recyclerView.setAdapter(taskListAdapter);

        // fungsiadd
        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_name = view.findViewById(R.id.ed_add_kegiatan);

                TaskList task = new TaskList();
                task.setName(txt_name.getText().toString());
                task.setDescription("");
                task.setStatus("0");

                db.taskListDao().insertTaskList(task);
                taskLists.clear();
                taskLists.addAll(db.taskListDao().getAll());
                taskListAdapter.notifyDataSetChanged();

                Toast.makeText(getContext(),"Data Ditambahkan",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    // fungsi ini untuk mengambil data dari database (tabel aktivitas)
    // lalu memasukannya kedalam list array 'aktivitasList'
    private List<Aktivitas> onLoad(View view){


        //mengisi data aktivitas List
        List<Aktivitas> act = db.aktivitasDao().getAll();
        return act;
    }

    private void showDialogUpdate(final Aktivitas aktivitas){
        dialog = new AppCompatDialog(getContext());
        dialog.setContentView(R.layout.dialog_aktivitas);
        dialog.setTitle("Ubah Aktifitas");

        final EditText ed_kegiatan = dialog.findViewById(R.id.ed_kegiatan);
        final TextView txt_id = dialog.findViewById(R.id.txt_id);
        int id = aktivitas.getId();
        ed_kegiatan.setText(aktivitas.getKegiatan());
        txt_id.setText(String.valueOf(id));

        Button btn_simpan = dialog.findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktivitas.setId(Integer.parseInt(txt_id.getText().toString()));
                aktivitas.setKegiatan(ed_kegiatan.getText().toString());
                aktivitas.setTime("");

                db.aktivitasDao().updateAktivitas(aktivitas);
                Toast.makeText(getContext(),"Update berhasil", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                aktivitasList.clear();
                aktivitasList.addAll(db.aktivitasDao().getAll());
                aktivitasAdapter.notifyDataSetChanged();
            }
        });

        Button btn_hapus = dialog.findViewById(R.id.btn_hapus);
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktivitas.setId(Integer.parseInt(txt_id.getText().toString()));
                aktivitas.setKegiatan(ed_kegiatan.getText().toString());
                aktivitas.setTime("");

                db.aktivitasDao().deleteAktivitas(aktivitas);

                Toast.makeText(getContext(),"Data terhapus", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                aktivitasList.clear();
                aktivitasList.addAll(db.aktivitasDao().getAll());
                aktivitasAdapter.notifyDataSetChanged();
            }
        });

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

}
