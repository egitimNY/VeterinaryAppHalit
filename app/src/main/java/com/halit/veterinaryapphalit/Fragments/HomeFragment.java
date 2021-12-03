package com.halit.veterinaryapphalit.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.halit.veterinaryapphalit.Adapters.AnswersAdapter;
import com.halit.veterinaryapphalit.Models.AnswerModel;
import com.halit.veterinaryapphalit.Models.AskQuestionModel;
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

public class HomeFragment extends Fragment {

    private View view;
    private LinearLayout petlerimLayout,soruSorLinerLayout,cevapLayout;
    private ChangeFragments changeFragments;
    private GetSharedPreferences getSharedPreferences;
    private String id;
    private AnswersAdapter answersAdapter;
    private List<AnswerModel> answerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimla();
        action();
        return view;
    }

    public void tanimla(){
        petlerimLayout = view.findViewById(R.id.petlerimLayout);
        soruSorLinerLayout = view.findViewById(R.id.soruSorLinerLayout);
        cevapLayout = view.findViewById(R.id.cevapLayout);
        answerList = new ArrayList<>();

        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        id = getSharedPreferences.getSession().getString("id",null);

    }

    public void action(){
        petlerimLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new UserPetsFragment());
            }
        });
        soruSorLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionAlert();
            }
        });
        cevapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(id);
            }
        });

    }



    public void openQuestionAlert(){
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.soru_sor_alert_dialog_layout,null);

        final EditText soruSorEditText = view.findViewById(R.id.soruSorEditText);
        MaterialButton soruSorButton = view.findViewById(R.id.soruSorButton);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        soruSorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String soru =  soruSorEditText.getText().toString();
              soruSorEditText.setText("");
                alertDialog.cancel();
                askQuestion(id,soru,alertDialog);
            }
        });
        alertDialog.show();

    }

    public void askQuestion(String mus_id, String text, final AlertDialog alert){
       Call<AskQuestionModel> req = ManagerAll.getInstance().soruSor(mus_id,text);
       req.enqueue(new Callback<AskQuestionModel>() {
           @Override
           public void onResponse(Call<AskQuestionModel> call, Response<AskQuestionModel> response) {
               if (response.body().isTf()){
                   Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                   alert.cancel();
               }
           }

           @Override
           public void onFailure(Call<AskQuestionModel> call, Throwable t) {
               Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
           }
       });
    }

    private void getAnswer(String mus_id) {
        Call<List<AnswerModel>> req = ManagerAll.getInstance().getAnswer(mus_id);
        req.enqueue(new Callback<List<AnswerModel>>() {
            @Override
            public void onResponse(Call<List<AnswerModel>> call, Response<List<AnswerModel>> response) {
                if (response.body().get(0).isTf()){
//                    Log.i( "cevaplarim",response.body().toString());
                    if (response.isSuccessful()) {
                        answerList = response.body();
                        answersAdapter = new AnswersAdapter(answerList,getContext());
                        openAnswerAlert();
                    }

                }else {
                    Toast.makeText(getContext(), "Herhangi bir cevap yok...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AnswerModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void openAnswerAlert(){
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.cevap_alert_layout,null);

        RecyclerView cevapRecyclerView = view.findViewById(R.id.cevapRecyclerView);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        cevapRecyclerView.setLayoutManager(layoutManager);
        cevapRecyclerView.setAdapter(answersAdapter);


        alertDialog.show();

    }


}
