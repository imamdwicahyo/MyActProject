package com.idea.idc.myacts.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.idea.idc.myacts.database.Converters;

import java.util.Date;


@Entity(tableName = "task_item",
        foreignKeys = @ForeignKey(entity = TaskList.class,
                                  parentColumns = "id_list",
                                  childColumns = "id_list"
                                  ))
public class TaskItem {
    @PrimaryKey(autoGenerate = true)
    private int id_task;

    @ColumnInfo(name = "id_list")
    private int id_list;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "date")
    @TypeConverters({Converters.class})
    private Date date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "status")
    private String status;

    public int getId_task() {
        return id_task;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
