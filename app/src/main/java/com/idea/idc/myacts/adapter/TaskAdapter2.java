package com.idea.idc.myacts.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.model.AktivitasModel;

import java.util.ArrayList;

public class TaskAdapter2 extends RecyclerView.Adapter<TaskAdapter2.TaskViewHolder> {

    ArrayList<AktivitasModel> aktivitas;

    public TaskAdapter2(ArrayList<AktivitasModel> aktivitas) {
        this.aktivitas = aktivitas;
    }

    @NonNull
    @Override
    public TaskAdapter2.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view =layoutInflater.inflate(R.layout.item_aktivitas,viewGroup,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.txt_kegiatan.setText(aktivitas.get(i).getKegiatan());
    }

    @Override
    public int getItemCount() {
        return aktivitas.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView txt_kegiatan;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_kegiatan = itemView.findViewById(R.id.txt_kegiatan);
        }
    }
}
