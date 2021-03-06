package com.odinperica.bicikli.view;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.odinperica.bicikli.R;
import com.odinperica.bicikli.viewModel.BiciklViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CUDFragment extends Fragment {

    static final int SLIKANJE =1;

    private String trenutnaPutanjaSlike;

    @BindView(R.id.imeBicikla)
    EditText imeBicikla;
    @BindView(R.id.vrsta_bicikla)
    Spinner vrsta_bicikla;
    @BindView(R.id.marka)
    EditText marka;
    @BindView(R.id.opis)
    EditText opis;
    @BindView(R.id.slika)
    ImageView slika;
    @BindView(R.id.noviBicikl)
    Button noviBicikl;
    @BindView(R.id.Slikaj)
    Button Slikaj;
    @BindView(R.id.promjenaBicikla)
    Button promjenaBicikla;
    @BindView(R.id.obrisiBicikl)
    Button obrisiBicikl;

    BiciklViewModel model;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,
                container, false);
        ButterKnife.bind(this, view);

        model = ((MainActivity) getActivity()).getModel();

        if (model.getBicikl().getId() == 0) {
            definirajNoviBicikl();
            return view;
        }
        definirajPromjenaBrisanjeBicikla();

        return view;
    }


    private void definirajPromjenaBrisanjeBicikla() {
        noviBicikl.setVisibility(View.GONE);
        vrsta_bicikla.setSelection(model.getBicikl().getVrsta_bicikla());
        imeBicikla.setText(model.getBicikl().getNazivBicikla());
        marka.setText(model.getBicikl().getMarkaBicikla());
        opis.setText(model.getBicikl().getOpisBicikla());
        Picasso.get().load(model.getBicikl().getSlikaBicikla()).error(R.drawable.bicikl).into(slika);

        Slikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uslikaj();
            }
        });

        promjenaBicikla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promjenaBicikla();
            }
        });

        obrisiBicikl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obrisiBicikl();
            }
        });


    }

    private void promjenaBicikla() {
        model.getBicikl().setNazivBicikla(imeBicikla.getText().toString());
        model.getBicikl().setVrsta_bicikla(vrsta_bicikla.getSelectedItemPosition());
        model.getBicikl().setMarkaBicikla(marka.getText().toString());
        model.getBicikl().setOpisBicikla(opis.getText().toString());
        model.promjeniBicikl();
        nazad();
    }

    private void definirajNoviBicikl() {
        promjenaBicikla.setVisibility(View.GONE);
        obrisiBicikl.setVisibility(View.GONE);
        Slikaj.setVisibility(View.GONE);
        noviBicikl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noviBicikl();
            }
        });
    }

    private void noviBicikl() {
        model.getBicikl().setNazivBicikla(imeBicikla.getText().toString());
        model.getBicikl().setVrsta_bicikla(vrsta_bicikla.getSelectedItemPosition());
        model.getBicikl().setMarkaBicikla(marka.getText().toString());
        model.getBicikl().setOpisBicikla(opis.getText().toString());
        model.dodajNoviBicikl();
        nazad();
    }


    private void obrisiBicikl() {
        model.getBicikl().setNazivBicikla(imeBicikla.getText().toString());
        model.getBicikl().setVrsta_bicikla(vrsta_bicikla.getSelectedItemPosition());
        model.getBicikl().setMarkaBicikla(marka.getText().toString());
        model.getBicikl().setOpisBicikla(opis.getText().toString());
        model.obrisiBicikl();
        nazad();
    }

    @OnClick(R.id.natrag)
    public void nazad() {
        ((MainActivity) getActivity()).read();
    }

    private void uslikaj() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) == null) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;

        }

        File slika = null;
        try {
            slika = kreirajDatotekuSlike();
        } catch (IOException ex) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
        }

        if (slika == null) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
        }

        Uri slikaURI = FileProvider.getUriForFile(getActivity(),"com.odinperica.bicikli.provider", slika);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, slikaURI);
        startActivityForResult(takePictureIntent, SLIKANJE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SLIKANJE && resultCode == Activity.RESULT_OK) {

            model.getBicikl().setSlikaBicikla("file://" + trenutnaPutanjaSlike);
            model.promjeniBicikl();
            Picasso.get().load(model.getBicikl().getSlikaBicikla()).into(slika);

        }
    }

    private File kreirajDatotekuSlike() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imeSlike = "BICIKL" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imeSlike,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        trenutnaPutanjaSlike = image.getAbsolutePath();
        return image;
    }

}
