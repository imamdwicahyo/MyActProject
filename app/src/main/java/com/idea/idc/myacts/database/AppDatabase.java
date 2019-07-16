package com.idea.idc.myacts.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.idea.idc.myacts.dao.AktivitasDao;
import com.idea.idc.myacts.entity.Aktivitas;

@Database(entities = Aktivitas.class, version = 1)
public abstract class AppDatabase  extends RoomDatabase {

    public abstract AktivitasDao aktivitasDao();
}
