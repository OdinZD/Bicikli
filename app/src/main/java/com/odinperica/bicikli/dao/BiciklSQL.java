package com.odinperica.bicikli.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.odinperica.bicikli.model.Bicikl;

import java.util.List;

@Dao
public interface BiciklSQL {

    @Query("Select * from bicikli order by id")
    LiveData<List<Bicikl>> dohvatiBicikle();

    @Insert
    public void dodajBicikl(Bicikl bicikl);

    @Delete
    public void obrisiBicikl(Bicikl bicikl);

    @Update
    public void promjeniBicikl(Bicikl bicikl);
}
