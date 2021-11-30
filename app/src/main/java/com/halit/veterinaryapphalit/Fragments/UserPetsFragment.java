package com.halit.veterinaryapphalit.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.halit.veterinaryapphalit.Models.PetModel;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.RestApi.ManagerAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPetsFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_pets, container, false);
        getPets("4");
        return view;
    }

    public void getPets(String mus_id){
        Call<List<PetModel>> req = ManagerAll.getInstance().getPets(mus_id);
        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                Log.i( "listem: ",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {

            }
        });
    }
}
