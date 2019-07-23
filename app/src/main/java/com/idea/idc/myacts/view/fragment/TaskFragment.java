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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.adapter.AktivitasAdapter;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.Aktivitas;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment{

    public static AppDatabase db;
    private RecyclerView recyclerView;
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


        aktivitasList = onLoad(view); // ambil data dari database

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

        // fungsiadd
        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed_add_kegiatan = view.findViewById(R.id.ed_add_kegiatan);

                Aktivitas aktivitas = new Aktivitas();
                aktivitas.setKegiatan(ed_add_kegiatan.getText().toString());
                aktivitas.setTime("");

                db.aktivitasDao().addAktivitas(aktivitas);
                Toast.makeText(getContext(),"Data Ditambahkan", Toast.LENGTH_SHORT).show();

                aktivitasList.clear();
                aktivitasList.addAll(db.aktivitasDao().getAll());
                aktivitasAdapter.notifyDataSetChanged();
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
