package com.idea.idc.myacts.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.idea.idc.myacts.dao.AktivitasDao;
import com.idea.idc.myacts.dao.TaskItemDao;
import com.idea.idc.myacts.dao.TaskListDao;
import com.idea.idc.myacts.entity.Aktivitas;
import com.idea.idc.myacts.entity.TaskItem;
import com.idea.idc.myacts.entity.TaskList;

@Database(entities = {Aktivitas.class, TaskList.class, TaskItem.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase  extends RoomDatabase {

    public abstract AktivitasDao aktivitasDao();

    public abstract TaskListDao taskListDao();

    public abstract TaskItemDao taskItemDao();
}
