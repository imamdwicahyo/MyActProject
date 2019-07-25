package com.idea.idc.myacts.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.idea.idc.myacts.entity.TaskList;

import java.util.List;

@Dao
public interface TaskListDao {

    @Query("SELECT * FROM task_list")
    List<TaskList> getAll();

    @Query("SELECT * FROM task_list WHERE id_list = :id")
    TaskList getTaskById(int id);

    @Insert
    void insertTaskList(TaskList taskList);

    @Update
    void updateTaskList(TaskList taskList);

    @Delete
    void deleteTaskList(TaskList taskList);
}
