package com.idea.idc.myacts.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "task_list")
public class TaskList {

    @PrimaryKey(autoGenerate = true)
    private int id_list;

    @ColumnInfo(name = "desc")
    private String description;

    @ColumnInfo(name = "status")
    private String status;

    public TaskList(int id_list, String description, String status) {
        this.id_list = id_list;
        this.description = description;
        this.status = status;
    }

    public int getId_list() {
        return id_list;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
