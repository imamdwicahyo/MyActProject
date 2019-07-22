package com.idea.idc.myacts.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.entity.Aktivitas;

import java.util.List;

public class AktivitasAdapter extends RecyclerView.Adapter<AktivitasAdapter.ViewHolder> {

    private List<Aktivitas> aktivitasList;
    private OnCallbackListener listener;

    public AktivitasAdapter(List<Aktivitas> aktivitas) {
        this.aktivitasList = aktivitas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_aktivitas, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Aktivitas aktivitas = aktivitasList.get(i);
        viewHolder.txt_kegiatan.setText(aktivitas.getKegiatan());
    }

    @Override
    public int getItemCount() {
        return aktivitasList.size();
    }

    public void setOnCallbackListener(OnCallbackListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_kegiatan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txt_kegiatan = itemView.findViewById(R.id.txt_kegiatan);
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onClick(aktivitasList.get(getAdapterPosition()));
            }
        }
    }

    public interface OnCallbackListener {
        void onClick(Aktivitas aktivitas);
    }
}
