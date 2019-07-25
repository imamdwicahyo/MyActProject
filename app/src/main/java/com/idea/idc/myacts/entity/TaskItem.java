package com.idea.idc.myacts.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;


@Entity(foreignKeys = @ForeignKey(entity = TaskList.class,
                                  parentColumns = "id_list",
                                  childColumns = "id_list"))
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
    private String date;

    @ColumnInfo(name = "time")
    private String time;
}
