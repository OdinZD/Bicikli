package com.odinperica.bicikli.viewModel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.odinperica.bicikli.dao.BiciklBaza;
import com.odinperica.bicikli.dao.BiciklSQL;
import com.odinperica.bicikli.model.Bicikl;

import java.util.List;

public class BiciklViewModel extends AndroidViewModel {

    BiciklSQL biciklSQl;
    private Bicikl bicikl;

    public Bicikl getBicikl() {
        return bicikl;
    }
    public void setBicikl(Bicikl bicikl){this.bicikl = bicikl;}

    private LiveData<List<Bicikl>> listaBicikla;


    public BiciklViewModel(Application application) {
        super(application);
        biciklSQl = BiciklBaza.getBaza(application.getApplicationContext()).biciklSQL();
    }

    public LiveData<List<Bicikl>> dohvatiBicikle(){
        listaBicikla = biciklSQl.dohvatiBicikle();
        return listaBicikla;
    }

    public void dodajNoviBicikl(){

        biciklSQl.dodajBicikl(bicikl);
    }

    public void promjeniBicikl(){

        biciklSQl.promjeniBicikl(bicikl);
    }

    public void obrisiBicikl(){

        biciklSQl.obrisiBicikl(bicikl);
    }
}
