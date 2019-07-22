package com.idea.idc.myacts.view.fragment;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.Aktivitas;

import java.util.ArrayList;
import java.util.List;

public class ProfilFragment extends Fragment {

    private Aktivitas aktivitas;
    public static AppDatabase db;

    List<Aktivitas> aktivitasList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profil, container, false);

        db = Room.databaseBuilder(getActivity(),AppDatabase.class,"aktivitas").allowMainThreadQueries().build();

        Button btn_simpan = view.findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kegiatan = "";
                String time = "";
                EditText ed_kegiatan = view.findViewById(R.id.ed_kegiatan);
                EditText ed_time = view.findViewById(R.id.ed_time);
                kegiatan = ed_kegiatan.getText().toString();
                time = ed_time.getText().toString();

                Aktivitas aktivitas = new Aktivitas();
                aktivitas.setKegiatan(kegiatan);
                aktivitas.setTime(time);

                db.aktivitasDao().addAktivitas(aktivitas);
                Log.d("SIMPAN","Data Tersimpan");
            }
        });

        return view;
    }
}
