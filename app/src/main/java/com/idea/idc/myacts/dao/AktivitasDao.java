package com.idea.idc.myacts.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.idea.idc.myacts.entity.Aktivitas;

import java.util.List;

@Dao
public interface AktivitasDao {

    @Query("SELECT * FROM aktivitas")
    List<Aktivitas> getAll();

    @Insert
    void addAktivitas(Aktivitas aktivitas);

    @Delete
    void deleteAktivitas(Aktivitas aktivitas);
}
