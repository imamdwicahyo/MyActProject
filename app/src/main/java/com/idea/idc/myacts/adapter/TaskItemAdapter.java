package com.idea.idc.myacts.adapter;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.TaskItem;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.ViewHolder> {

    List<TaskItem> taskItems;
    private OnCallbackListener listener;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    public TaskItemAdapter(List<TaskItem> taskItems) {
        this.taskItems = taskItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_task_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vewHolder, int i) {
        TaskItem task = taskItems.get(i);
        vewHolder.txt_id_task.setText(String.valueOf(task.getId_task()));
        vewHolder.txt_listItem_nama.setText(task.getName());
        if (task.getDate() != null){
            vewHolder.txt_taskItem_tanggal.setText(dateFormatter.format(task.getDate()));
        }else{
            vewHolder.txt_taskItem_tanggal.setText("");
        }
        vewHolder.getTxt_taskItem_waktu.setText(task.getTime());
        int status = 0;
        if (task.getStatus() != null){
            status = Integer.parseInt(task.getStatus());
        }

        if (status > 0){
            vewHolder.check_taskItem.setChecked(true);
        }else{
            vewHolder.check_taskItem.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    public void setOnCallbackListener(OnCallbackListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppDatabase db;
        TextView txt_id_task;
        TextView txt_listItem_nama;
        TextView txt_taskItem_tanggal;
        TextView getTxt_taskItem_waktu;
        CheckBox check_taskItem;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            db = Room.databaseBuilder(itemView.getContext(),AppDatabase.class,"aktivitas")
                    .allowMainThreadQueries().build();

            txt_id_task = itemView.findViewById(R.id.txt_id_task);
            txt_listItem_nama = itemView.findViewById(R.id.txt_taskItem_name);
            txt_taskItem_tanggal = itemView.findViewById(R.id.txt_taskItem_tanggal);
            getTxt_taskItem_waktu = itemView.findViewById(R.id.txt_taskItem_waktu);
            check_taskItem = itemView.findViewById(R.id.check_taskItem);
            check_taskItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = Integer.valueOf(txt_id_task.getText().toString());
                    TaskItem taskItem = db.taskItemDao().getTaskItemByIdTask(id);
                    if (check_taskItem.isChecked()){
                        taskItem.setStatus("1");
                        Toast.makeText(itemView.getContext(),txt_id_task.getText(),Toast.LENGTH_SHORT).show();
                    }else{
                        taskItem.setStatus("0");
                        Toast.makeText(itemView.getContext(),"False",Toast.LENGTH_SHORT).show();
                    }
                    db.taskItemDao().updateTaskItem(taskItem);
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onClick(taskItems.get(getAdapterPosition()));
            }
        }
    }

    public interface OnCallbackListener {
        void onClick(TaskItem task);
    }
}
