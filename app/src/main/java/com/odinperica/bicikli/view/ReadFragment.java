package com.odinperica.bicikli.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.odinperica.bicikli.R;
import com.odinperica.bicikli.adapter.BiciklAdapter;
import com.odinperica.bicikli.adapter.BiciklClickListener;
import com.odinperica.bicikli.model.Bicikl;
import com.odinperica.bicikli.viewModel.BiciklViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadFragment extends Fragment {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;

    private BiciklViewModel model;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);

        model = ((MainActivity)getActivity()).getModel();

        definirajListu();
        definirajSwipe();
        osvjeziPodatke();


        return view;
    }

    private void osvjeziPodatke(){
        model.dohvatiBicikle().observe(this, new Observer<List<Bicikl>>() {
            @Override
            public void onChanged(@Nullable List<Bicikl> Bicikli) {
                swipeRefreshLayout.setRefreshing(false);
                ((BiciklAdapter)listView.getAdapter()).setListaBicikla(Bicikli);
                ((BiciklAdapter) listView.getAdapter()).osvjeziPodatke();

            }
        });
    }

    private void definirajSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                osvjeziPodatke();
            }
        });

    }

    private void definirajListu() {

        listView.setAdapter( new BiciklAdapter(getActivity(), R.layout.red_liste, new BiciklClickListener() {
            @Override
            public void onItemClick(Bicikl bicikl) {
                model.setBicikl(bicikl);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

    @OnClick(R.id.fab)
    public void novoBicikl(){
        model.setBicikl(new Bicikl());
        ((MainActivity)getActivity()).cud();
    }

}