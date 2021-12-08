package com.halit.veterinaryapphalit.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halit.veterinaryapphalit.Adapters.KampanyaAdapter;
import com.halit.veterinaryapphalit.Models.KampanyaModel;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.RestApi.ManagerAll;
import com.halit.veterinaryapphalit.Utils.ChangeFragments;
import com.halit.veterinaryapphalit.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampanyaFragment extends Fragment {

    private View view;
    private RecyclerView kampanyaRecView;
    private ChangeFragments changeFragments;
    private KampanyaAdapter kampanyaAdapter;
    private List<KampanyaModel> kampanyaList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kampanya, container, false);
        tanimla();
        getKampanya();
        return view;
    }

    private void tanimla() {
        kampanyaRecView = (RecyclerView) view.findViewById(R.id.kampanyaRecView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        kampanyaRecView.setLayoutManager(layoutManager);
        changeFragments = new ChangeFragments(getContext());
        kampanyaList = new ArrayList<>();
    }

    public void getKampanya(){
        Call<List<KampanyaModel>> req = ManagerAll.getInstance().getKampanya();
        req.enqueue(new Callback<List<KampanyaModel>>() {
            @Override
            public void onResponse(Call<List<KampanyaModel>> call, Response<List<KampanyaModel>> response) {

                Log.i( "kampanyalar",response.body().toString());
                if (response.body().get(0).isTf())
                {
                    kampanyaList = response.body();
                    kampanyaAdapter = new KampanyaAdapter(kampanyaList,getContext());
                    kampanyaRecView.setAdapter(kampanyaAdapter);


                }else {
                    Toast.makeText(getContext(), "Herhangi bir kampanya bulunmamaktadir..", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }

            }

            @Override
            public void onFailure(Call<List<KampanyaModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

}
