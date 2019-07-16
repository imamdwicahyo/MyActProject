package com.idea.idc.myacts.model;

public class AktivitasModel {

    private int id;
    private String kegiatan;
    private String time;

    public AktivitasModel(int id, String kegiatan, String time) {
        this.id = id;
        this.kegiatan = kegiatan;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public String getTime() {
        return time;
    }
}
