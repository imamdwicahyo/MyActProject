package com.idea.idc.myacts.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.idea.idc.myacts.entity.TaskItem;

import java.util.List;

@Dao
public interface TaskItemDao {

    @Query("SELECT * FROM task_item")
    List<TaskItem> getAll();

    @Query("SELECT * FROM task_item WHERE id_list = :id")
    List<TaskItem> getTaskItemById(int id);

    @Insert
    void insertTaskItem(TaskItem taskItem);

    @Update
    void updateTaskItem(TaskItem taskItem);

    @Delete
    void deleteTaskItem(TaskItem taskItem);
}
