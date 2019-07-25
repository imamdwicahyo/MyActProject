package com.idea.idc.myacts.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "task_list")
public class TaskList {

    @PrimaryKey(autoGenerate = true)
    private int id_list;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "desc")
    private String description;

    @ColumnInfo(name = "status")
    private String status;

    public int getId_list() {
        return id_list;
    }

    public void setId_list(int id_list) {
        this.id_list = id_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
