package com.halit.veterinaryapphalit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.halit.veterinaryapphalit.Models.AnswerModel;
import com.halit.veterinaryapphalit.Models.DeleteAnswerModel;
import com.halit.veterinaryapphalit.R;
import com.halit.veterinaryapphalit.RestApi.ManagerAll;
import com.halit.veterinaryapphalit.Utils.Warnings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder>{

    List<AnswerModel> list;
    Context context;

    public AnswersAdapter(List<AnswerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cevap_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.cevapSoruText.setText("Soru : " +list.get(position).getSoru().toString());
        holder.cevapText.setText("\r\nCevap : " +list.get(position).getCevap().toString());

        holder.cevapSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteToService(list.get(position).getCevapid(),list.get(position).getSoruid(),position);
//                Log.i( "idlerim ",list.get(position).getCevapid()+"--"+list.get(position).getSoruid());
//                Log.i( "idlerim ","Tiklandi");

            }
        });
    }

    public void deleteToList(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    private void deleteToService(String cevapid, String soruid, final int pos){
        Call<DeleteAnswerModel> req = ManagerAll.getInstance().deleteAnswer(cevapid,soruid);
        req.enqueue(new Callback<DeleteAnswerModel>() {
            @Override
            public void onResponse(Call<DeleteAnswerModel> call, Response<DeleteAnswerModel> response) {
                if (response.body().isTf()){
                    if (response.isSuccessful()) {
                        Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                        deleteToList(pos);
                    }
                    }else {
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAnswerModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cevapSoruText,cevapText;
        MaterialButton cevapSilButon;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            cevapSoruText = itemView.findViewById(R.id.cevapSoruText);
            cevapText = itemView.findViewById(R.id.cevapText);
            cevapSilButon = itemView.findViewById(R.id.cevapSilButon);

        }
    }
}
