package com.odinperica.bicikli.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;



@Entity(tableName = "bicikli")
public class Bicikl implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivBicikla() {
        return nazivBicikla;
    }

    public void setNazivBicikla(String nazivBicikla) {
        this.nazivBicikla = nazivBicikla;
    }

    public String getMarkaBicikla() {
        return markaBicikla;
    }

    public void setMarkaBicikla(String markaBicikla) {
        this.markaBicikla = markaBicikla;
    }

    public String getOpisBicikla() {
        return opisBicikla;
    }

    public void setOpisBicikla(String opisBicikla) {
        this.opisBicikla = opisBicikla;
    }

    public String getSlikaBicikla() {
        return slikaBicikla;
    }

    public void setSlikaBicikla(String slikaBicikla) {
        this.slikaBicikla = slikaBicikla;
    }

    private String nazivBicikla;
    private String markaBicikla;
    private String opisBicikla;
    private String slikaBicikla;
    private int vrsta_bicikla;

    public int getVrsta_bicikla() {
        return vrsta_bicikla;
    }

    public void setVrsta_bicikla(int vrsta_bicikla) {
        this.vrsta_bicikla = vrsta_bicikla;
    }
}
