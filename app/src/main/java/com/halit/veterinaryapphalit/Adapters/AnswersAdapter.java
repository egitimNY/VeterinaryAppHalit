package com.halit.veterinaryapphalit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.halit.veterinaryapphalit.Models.AnswerModel;
import com.halit.veterinaryapphalit.R;

import java.util.List;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.cevapSoruText.setText("Soru : " +list.get(position).getSoru().toString());
        holder.cevapText.setText("\r\nCevap : " +list.get(position).getCevap().toString());

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
