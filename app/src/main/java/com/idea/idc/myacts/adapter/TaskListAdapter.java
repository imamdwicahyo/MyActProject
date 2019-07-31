package com.idea.idc.myacts.adapter;

import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.TaskList;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<TaskList> taskLists;
    private OnCallbackListener listener;

    public TaskListAdapter(List<TaskList> taskLists) {
        this.taskLists = taskLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_task_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TaskList task = taskLists.get(i);
        viewHolder.txt_taskList_name.setText(task.getName());
        viewHolder.txt_taskList_all.setText(String.valueOf(viewHolder.db.taskItemDao().countTask(task.getId_list())));
        viewHolder.txt_taskList_check.setText(String.valueOf(viewHolder.db.taskItemDao().countTaskSuccess(task.getId_list())));

    }

    @Override
    public int getItemCount() {
        return taskLists.size();
    }

    public void setOnCallbackListener(OnCallbackListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txt_taskList_all;
        TextView txt_taskList_name;
        TextView txt_taskList_check;
        AppDatabase db;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            
            txt_taskList_name = itemView.findViewById(R.id.txt_taskList_name);
            txt_taskList_all = itemView.findViewById(R.id.txt_taskList_all);
            txt_taskList_check = itemView.findViewById(R.id.txt_taskList_check);

            db = Room.databaseBuilder(itemView.getContext(),AppDatabase.class,"aktivitas")
                    .allowMainThreadQueries().build();
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onClick(taskLists.get(getAdapterPosition()));
            }
        }
    }

    public interface OnCallbackListener {
        void onClick(TaskList task);
    }
}
