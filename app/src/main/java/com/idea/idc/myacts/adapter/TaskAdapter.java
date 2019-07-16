package com.idea.idc.myacts.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.entity.Aktivitas;
import com.idea.idc.myacts.model.AktivitasModel;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<AktivitasModel> aktivitasModels;
    private OnCallBackListener listener;

    public TaskAdapter(List<AktivitasModel> aktivitasModels) {
        this.aktivitasModels = aktivitasModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_aktivitas,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AktivitasModel aktivitas = aktivitasModels.get(i);
        viewHolder.txt_kegiatan.setText(aktivitas.getKegiatan());
    }

    @Override
    public int getItemCount() {
        return aktivitasModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_kegiatan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txt_kegiatan = itemView.findViewById(R.id.txt_kegiatan);
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onClick(aktivitasModels.get(getAdapterPosition()));
            }
        }
    }

    public interface OnCallBackListener{
        void onClick(AktivitasModel user);
    }
}
