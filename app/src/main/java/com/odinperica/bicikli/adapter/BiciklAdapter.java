package com.odinperica.bicikli.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.odinperica.bicikli.R;
import com.odinperica.bicikli.model.Bicikl;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BiciklAdapter extends ArrayAdapter<Bicikl> {

    public List<Bicikl> getListaBicikla() {
        return listaBicikla;
    }

    public void setListaBicikla(List<Bicikl> listaBicikla) {
        this.listaBicikla = listaBicikla;
    }

    private List<Bicikl> listaBicikla;
    private BiciklClickListener biciklClickListener;
    private Context context;
    private int resource;

    public BiciklAdapter(@NonNull Context context, int resource, BiciklClickListener biciklClickListener) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.biciklClickListener = biciklClickListener;
    }

    private static class ViewHolder{

        private ImageView slika;
        private TextView nazivBicikla;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        final Bicikl bicikl;
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(this.resource, null);

                viewHolder.nazivBicikla = view.findViewById(R.id.vrsta_bicikla);
                viewHolder.slika = view.findViewById(R.id.slika);
            } else {
                viewHolder = (ViewHolder) view.getTag();

            }

            bicikl = getItem(position);

            if (bicikl != null) {

                viewHolder.nazivBicikla.setText(bicikl.getNazivBicikla() + " - " + context.getResources().getStringArray(R.array.vrsta_bicikla)[bicikl.getVrsta_bicikla()]);

                if (bicikl.getSlikaBicikla() == null) {
                    Picasso.get().load(R.drawable.bicikl).fit().centerCrop().into(viewHolder.slika);
                } else {
                    Picasso.get().load(bicikl.getSlikaBicikla()).fit().centerCrop().into(viewHolder.slika);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    biciklClickListener.onItemClick(bicikl);
                }
            });
        }
        return view;
    }

    @Override
    public int getCount() {
        return listaBicikla == null ? 0 : listaBicikla.size();
    }

    @Nullable
    @Override
    public Bicikl getItem(int position) {
        return listaBicikla.get(position);
    }

    public void setPodaci(List<Bicikl> Bicikla) {
        this.listaBicikla = Bicikla;
    }

    public void osvjeziPodatke() {
        notifyDataSetChanged();
    }
}
