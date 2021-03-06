package com.odinperica.bicikli.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.odinperica.bicikli.model.Bicikl;


//singleton
@Database(entities = {Bicikl.class}, version = 1, exportSchema = false)
public abstract class BiciklBaza extends RoomDatabase {

    public abstract BiciklSQL biciklSQL();

    private static BiciklBaza bazaHandler;

    public static BiciklBaza getBaza(Context context){
        if(bazaHandler == null){
            bazaHandler = Room.databaseBuilder(context.getApplicationContext(), BiciklBaza.class, "bicikl-baza").allowMainThreadQueries().build();
        }
        return bazaHandler;
    };

    public static void deleteInstance(){
        bazaHandler = null;
    }
}
