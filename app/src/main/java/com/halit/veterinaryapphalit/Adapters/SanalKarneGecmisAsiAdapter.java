package com.halit.veterinaryapphalit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.halit.veterinaryapphalit.Models.AsiModel;
import com.halit.veterinaryapphalit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarneGecmisAsiAdapter extends RecyclerView.Adapter<SanalKarneGecmisAsiAdapter.ViewHolder>{

    List<AsiModel> list;
    Context context;

    public SanalKarneGecmisAsiAdapter(List<AsiModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanal_karne_gecmis_asi_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.sanalKarneGecmisAsiText.setText(list.get(position).getAsiisim().toString()+" asisi yapilmistir.");
        holder.sanalKarneGecmisAsiBilgi.setText(list.get(position).getPetisim().toString()+" isimli petinize"
        +"\r"+list.get(position).getAsitarih()+ " tarihinde " + list.get(position).getAsiisim()+" asisi yapilmistir.");
        Picasso.get().load(list.get(position).getPetresim().toString()).into(holder.sanalKarneGecmisAsiImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView sanalKarneGecmisAsiText,sanalKarneGecmisAsiBilgi;
        CircleImageView sanalKarneGecmisAsiImage;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            sanalKarneGecmisAsiText = itemView.findViewById(R.id.sanalKarneGecmisAsiText);
            sanalKarneGecmisAsiBilgi = itemView.findViewById(R.id.sanalKarneGecmisAsiBilgi);
            sanalKarneGecmisAsiImage = itemView.findViewById(R.id.sanalKarneGecmisAsiImage);
        }
    }
}
