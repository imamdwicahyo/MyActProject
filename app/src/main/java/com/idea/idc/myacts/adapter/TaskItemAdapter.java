package com.idea.idc.myacts.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.entity.TaskItem;

import java.util.List;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.ViewHolder> {

    List<TaskItem> taskItems;
    private OnCallbackListener listener;

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
        vewHolder.txt_listItem_nama.setText(task.getName());
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    public void setOnCallbackListener(OnCallbackListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_listItem_nama;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txt_listItem_nama = itemView.findViewById(R.id.txt_taskItem_name);
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
