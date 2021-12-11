package com.halit.veterinaryapphalit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halit.veterinaryapphalit.Adapters.SanalKarnePetAdapter;
import com.halit.veterinaryapphalit.Models.PetModel;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.RestApi.ManagerAll;
import com.halit.veterinaryapphalit.Utils.ChangeFragments;
import com.halit.veterinaryapphalit.Utils.GetSharedPreferences;
import com.halit.veterinaryapphalit.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanalKarnePetlerFragment extends Fragment {

    private RecyclerView sanalKarnePetler;
    private View view;
    private SanalKarnePetAdapter sanalKarnePetAdapter;
    private List<PetModel> petList;
    private ChangeFragments changeFragments;
    private String mus_id;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sanal_karne_petler, container, false);


        tanimla();
//        getPets("4");
        getPets(mus_id);
        return view;
    }

    public void tanimla(){
        petList = new ArrayList<>();
        sanalKarnePetler = view.findViewById(R.id.sanalKarnePetler);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        sanalKarnePetler.setLayoutManager(mng);
        changeFragments =new ChangeFragments(getContext());
        getSharedPreferences =new GetSharedPreferences(getActivity());
        mus_id = getSharedPreferences.getSession().getString("id",null);
    }

    public void getPets(String mus_id){
        Call<List<PetModel>> req = ManagerAll.getInstance().getPets(mus_id);
        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
//                Log.i( "listem: ",response.body().toString());
                if (response.body().get(0).isTf()){
                    petList = response.body();
                    sanalKarnePetAdapter = new SanalKarnePetAdapter(petList,getContext());
                    sanalKarnePetler.setAdapter(sanalKarnePetAdapter);
//                    Toast.makeText(getContext(), "Sistemde kayitli " +petList.size() + " petiniz bulunmaktadir", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), " Sistemde kayitli petiniz bulunmamaktadir ", Toast.LENGTH_SHORT).show();
//                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }
}
