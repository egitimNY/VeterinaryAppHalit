package com.halit.veterinaryapphalit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.halit.veterinaryapphalit.Models.KampanyaModel;
import com.halit.veterinaryapphalit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.ViewHolder>{

    List<KampanyaModel> list;
    Context context;

    public KampanyaAdapter(List<KampanyaModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kampanya_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.kampanyaBaslikText.setText(list.get(position).getBaslik().toString());
        holder.kampanyaText.setText(list.get(position).getText().toString());
        Picasso.get().load(list.get(position).getResim()).into(holder.kampanyaImageView);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView kampanyaBaslikText,kampanyaText;
        ImageView kampanyaImageView;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            kampanyaText = itemView.findViewById(R.id.kampanyaText);
            kampanyaBaslikText = itemView.findViewById(R.id.kampanyaBaslikText);
            kampanyaImageView = itemView.findViewById(R.id.kampanyaImageView);

        }
    }
}
